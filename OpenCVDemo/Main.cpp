# include <iostream>
# include <opencv2/opencv.hpp>

using namespace cv;
using namespace std;

void rectToImage(Mat image, const CvRect rect, Mat result)
{
	for (int i = 0; i < rect.height; i++) {
		for (int j = 0; j < rect.width; j++) {
			//cout << rect.width << " " << rect.height << " " << j << " " << i << endl;
			//cout << "123" << image.at<uchar>(rect.x + j, rect.y + i) << endl;
			result.at<uchar>(j, i) = image.at<uchar>(rect.x + j, rect.y + i);
		}
	}
}

int main() {  
	VideoCapture cap(0);

	while (waitKey(30) != 27) {
		Mat frame;
		cap >> frame;
		namedWindow("原图");
		imshow("原图", frame);

		//灰度图
		Mat grey = frame;
		cvtColor(frame, grey, COLOR_RGB2GRAY);
		cvNamedWindow("灰度化");
		imshow("灰度化", grey);

		//Canny 边缘检测
		int lowTresholde = 30;
		int highTresholde = lowTresholde * 3;
		Mat canny = grey;
		Canny(grey, canny, lowTresholde, highTresholde, 3);
		cvNamedWindow("Canny");
		imshow("Canny", canny);

		//膨胀
		Mat dilation;
		Mat element2 = getStructuringElement(MORPH_RECT, Size(5, 5));//膨胀、腐蚀操作核设定
		dilate(canny, dilation, element2);
		//erode(dilation, dilation, element2);
		cvNamedWindow("膨胀");
		imshow("膨胀", dilation);

		Mat mat = dilation;
		dilation.convertTo(mat, CV_8UC1);

		vector<vector<Point>> contours;
		vector<Vec4i> hierarchy;
		vector<RotatedRect> rects;
		findContours(mat, contours, hierarchy, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE, Point(0, 0));

		cout << contours.size() << endl;

		for (int i = 0; i < contours.size(); i++)
		{
			//计算轮廓面积
			double area = contourArea(contours[i]);
			if (area < 200)
				continue;

			//轮廓近似，作用较小，approxPolyDP函数有待研究
			double epsilon = 0.001*arcLength(contours[i], true);
			Mat approx;
			approxPolyDP(contours[i], approx, epsilon, true);

			//找到最小矩形，该矩形可能有方向
			RotatedRect rect = minAreaRect(contours[i]);

			//计算高和宽
			int m_width = rect.boundingRect().width;
			int m_height = rect.boundingRect().height;

			//筛选那些太细的矩形，留下扁的
			if (m_height > m_width * 3 || m_width > m_height * 3)
				continue;

			//符合条件的rect添加到rects集合中
			rects.push_back(rect);

		}

		cout << rects.size() << endl;

		int j = 0;

		for each (RotatedRect rect in rects)
		{
			Point2f P[4];
			rect.points(P);

			rectangle(frame, P[1], P[3], Scalar(0, 255, 0));
			int lx = P[1].x;
			int ly = P[1].y;
			int height = P[3].x - P[1].x;
			int width = P[3].y - P[1].y;

			cout << lx << endl;
			cout << ly << endl;
			cout << height << endl;
			cout << width << endl;
			cout << " " << endl;
		}
		namedWindow("辨别");
		imshow("辨别", frame);
	}


	//Mat img = imread("test7.jpg");
	//resize(img, img, Size(300, 300));
	//namedWindow("原图");
	//imshow("原图", img);

	///*Mat mat = Mat(200, 200, CV_8U);
	//rectToImage(img, Rect(0, 0, 200, 200), mat);
	//namedWindow("原图1");
	//imshow("原图1", mat);*/

	//降噪
	///*Mat blur = img;
	//GaussianBlur(img, blur, Size(3, 3), 0, 0, BORDER_DEFAULT);
	//cvNamedWindow("降噪");
	//imshow("降噪", blur);*/

	//灰度图
	//Mat grey = img;
	//cvtColor(img, grey, COLOR_RGB2GRAY);
	//cvNamedWindow("灰度化");
	//imshow("灰度化", grey);

	//Canny 边缘检测
	//int lowTresholde = 30;
	//int highTresholde = lowTresholde * 3;
	//Mat canny = grey;
	//Canny(grey, canny, lowTresholde, highTresholde, 3);
	//cvNamedWindow("Canny");
	//imshow("Canny", canny);

	//膨胀
	//Mat dilation;
	//Mat element2 = getStructuringElement(MORPH_RECT, Size(5, 5));//膨胀、腐蚀操作核设定
	//dilate(canny,dilation ,element2);
	//erode(dilation, dilation, element2);
 //   cvNamedWindow("膨胀");
	//imshow("膨胀", dilation);

	//Mat mat = dilation;
	//dilation.convertTo(mat, CV_8UC1);

	//vector<vector<Point>> contours;
	//vector<Vec4i> hierarchy;
	//vector<RotatedRect> rects;
	//findContours(mat, contours, hierarchy, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE, Point(0, 0));

	//cout << contours.size() << endl;

	//for (int i = 0; i < contours.size(); i++)
	//{
	//	计算轮廓面积
	//	double area = contourArea(contours[i]);
	//	if (area < 200)
	//		continue;

	//	轮廓近似，作用较小，approxPolyDP函数有待研究
	//	double epsilon = 0.001*arcLength(contours[i], true);
	//	Mat approx;
	//	approxPolyDP(contours[i], approx, epsilon, true);

	//	找到最小矩形，该矩形可能有方向
	//	RotatedRect rect = minAreaRect(contours[i]);

	//	计算高和宽
	//	int m_width = rect.boundingRect().width;
	//	int m_height = rect.boundingRect().height;

	//	筛选那些太细的矩形，留下扁的
	//	if (m_height > m_width * 3 || m_width > m_height * 3)
	//		continue;

	//	符合条件的rect添加到rects集合中
	//	rects.push_back(rect);

	//}

	//cout << rects.size() << endl;

	//int j = 0;

	//for each (RotatedRect rect in rects)
	//{
	//	Point2f P[4];
	//	rect.points(P);

	//	rectangle(img, P[1], P[3], Scalar(0, 255, 0));
	//	int lx = P[1].x;
	//	int ly = P[1].y;
	//	int height = P[3].x - P[1].x;
	//	int width = P[3].y - P[1].y;

	//	cout << lx << endl;
	//	cout << ly << endl;
	//	cout << height << endl;
	//	cout << width << endl;
	//	cout << " " << endl;
	//}
	//namedWindow("辨别");
	//imshow("辨别", img);


	waitKey(0);
	return 0;
}