package androidlab.nuc.edu.cn.dynamicanimationdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fling_bt.setOnClickListener {
            val fling : FlingAnimation = FlingAnimation(image, DynamicAnimation.X) // FlingAnimation 初速度为 0 pixels/s
            fling.setStartVelocity(500f) // 赋予初速度
            fling.friction = 0.5f // 摩擦系数
            fling.start()
        }

        spring_bt.setOnClickListener {
            val springAnimation : SpringAnimation = SpringAnimation(image, DynamicAnimation.X)
            springAnimation.setStartVelocity(2000f)

            val springForce = SpringForce()
            springForce.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY // 弹性阻尼
            springForce.stiffness = SpringForce.STIFFNESS_LOW // 生硬度
            springForce.finalPosition = image.x

            springAnimation.spring = springForce
            springAnimation.start()
        }


    }

















}
