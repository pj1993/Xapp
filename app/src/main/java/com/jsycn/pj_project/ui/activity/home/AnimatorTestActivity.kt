package com.jsycn.pj_project.ui.activity.home

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.dynamicanimation.animation.FloatValueHolder
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.jsycn.pj_project.R

class AnimatorTestActivity : AppCompatActivity() {
    private lateinit var vTranslationY: ConstraintLayout
    private lateinit var ivMin1: ImageView//透明动画x1
    private lateinit var ivMin2: ImageView

    private lateinit var ivBig1: ImageView//透明动画d1
    private lateinit var ivBig2: ImageView
    private lateinit var llAction: View//透明动画d2
    private lateinit var mViewBg: View

    var constraintSet : ConstraintSet?=null



    var objectAnimator: ObjectAnimator? = null


    var valueAnimator: ValueAnimator? = null
    var alphaAnimatorX1: ValueAnimator? = null
    var alphaAnimatorD1: ValueAnimator? = null
    var alphaAnimatorD2: ValueAnimator? = null

    var mIsMediaExpanding = false
    var mIsViewExpanding = false

    private  var maxHeight :Int = 0
    private  var minHeight :Int =0

    private  var scaleMaxSize :Int = 0
    private  var scaleMinSize :Int =0
    private fun dp2Px(context: Context, dp: Float): Int {
        val displayMetrics = context.resources.displayMetrics
        return (TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            displayMetrics
        ) + 0.5).toInt()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_animator_test)
        ivMin1 =findViewById(R.id.ivMin1)
        ivMin2 = findViewById(R.id.ivMin2)
        ivBig1 = findViewById(R.id.ivBig1)
        ivBig2 = findViewById(R.id.ivBig2)
        llAction = findViewById(R.id.llAction)
        maxHeight = dp2Px(this,412f)
        minHeight = dp2Px(this,188f)
        scaleMaxSize = dp2Px(this,62f)
        scaleMinSize = dp2Px(this,40f)
        vTranslationY = findViewById(R.id.vTranslationY)
        constraintSet = ConstraintSet().apply { clone(vTranslationY) }
        constraintSet?.setAlpha(R.id.ivBig1,0f)
        findViewById<View>(R.id.cdView).setOnClickListener {
            if (mIsMediaExpanding) {
                refreshAnimate(false)
            } else {
                refreshAnimate(true)
            }
        }

        mViewBg = findViewById(R.id.vBg2)
        mViewBg.setOnClickListener {
            if (mIsViewExpanding){
                refreshViewAnimate(false)
            }else{
                refreshViewAnimate(true)
            }
        }

    }


    private var springAnimation: SpringAnimation?=null

    fun getAnimate(toExpand: Boolean): ValueAnimator? {

        if (springAnimation == null){
            springAnimation = SpringAnimation(FloatValueHolder(if (toExpand) maxHeight.toFloat() else minHeight.toFloat()))
            springAnimation?.apply {
                setStartValue(vTranslationY.layoutParams.height.toFloat())
                spring = SpringForce(if (toExpand) maxHeight.toFloat() else minHeight.toFloat())
                spring.setStiffness(230f)
                spring.setDampingRatio(0.85f)
                addUpdateListener {animation,value,velocity->
                    /*constraintSet?.let {
                        it.getConstraint(R.id.vTranslationY).layout.mHeight = value.toInt()
                        it.applyTo(vTranslationY)
                    }*/
                    val lp = vTranslationY.layoutParams
                    lp.height = value.toInt()
                    vTranslationY.layoutParams = lp
                }
                addEndListener{ animation,  canceled,  value, velocity->
                    /*val finalHeight = if (mIsMediaExpanding) maxHeight else minHeight
                    constraintSet?.let {
                        it.getConstraint(R.id.vTranslationY).layout.mHeight = finalHeight
                        it.applyTo(vTranslationY)
                    }*/
                    val lp = vTranslationY.layoutParams
                    if (mIsMediaExpanding) {
                        lp.height = maxHeight
                    } else {
                        lp.height = minHeight
                    }
                    vTranslationY.layoutParams = lp
                }
            }
        }else{
            springAnimation?.spring?.setFinalPosition(if (toExpand) maxHeight.toFloat() else minHeight.toFloat())
        }
        springAnimation?.start()

        /*if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt().apply {
                    setIntValues(minHeight, maxHeight)
                    setDuration(1500)
                    setInterpolator {
                        val factor = 0.85
                        (pow(
                            2.0,
                            -23.0 * it
                        ) * sin((it - factor / 4) * (2 * PI) / factor) + 1).toFloat()
                    }

                    addUpdateListener {

                        val lp = vTranslationY.layoutParams
                        lp.height = it.getAnimatedValue() as Int
                        vTranslationY.layoutParams = lp

                    }

                    addListener(object :
                        Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {
                            Log.d("MainActivity_", "onAnimationStart: ")
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            //animation.is()
                            Log.d("MainActivity_", "onAnimationEnd: ")
                            val lp = vTranslationY.layoutParams
                            if (mIsMediaExpanding) {
                                lp.height = maxHeight
                            } else {
                                lp.height = minHeight
                            }
                            vTranslationY.layoutParams = lp
                        }

                        override fun onAnimationCancel(animation: Animator) {
                            Log.d("MainActivity_", "onAnimationCancel: ")
                        }

                        override fun onAnimationRepeat(animation: Animator) {
                            Log.d("MainActivity_", "onAnimationRepeat: ")
                        }
                    })
                }
        } else {

            valueAnimator?.setValues(
                PropertyValuesHolder.ofInt("", vTranslationY.layoutParams.height,
                    if (toExpand) maxHeight else minHeight)
            )
        }


        *//*val springAnimation = SpringAnimation(vTranslationY)
        springAnimation.spring.stiffness = 230f
        springAnimation.spring.dampingRatio = 0.85f*//*

        return valueAnimator!!*/
        return null
    }

    private var scaleAnimation: SpringAnimation?=null
    fun scaleMinIcon(toExpand: Boolean){
        if (scaleAnimation==null){
            scaleAnimation = SpringAnimation(FloatValueHolder(if (toExpand) scaleMinSize.toFloat() else scaleMaxSize.toFloat()))
            scaleAnimation?.apply {
                setStartValue(if (toExpand) scaleMaxSize.toFloat() else scaleMinSize.toFloat())
                spring = SpringForce(if (toExpand) scaleMinSize.toFloat() else scaleMaxSize.toFloat())
                spring.setStiffness(300f)
                spring.setDampingRatio(0.9f)
                addUpdateListener {animation,value,velocity->
                    constraintSet?.let {
                        it.getConstraint(R.id.ivMin1).layout.apply {
                            mHeight = value.toInt()
                            mWidth = value.toInt()
                        }
                        it.applyTo(vTranslationY)
                    }

                    /*val lp = ivMin1.layoutParams
                    lp.height = value.toInt()
                    lp.width = value.toInt()
                    ivMin1.layoutParams = lp*/
                }
                addEndListener{ animation,  canceled,  value, velocity->
                    val finalSize = if (mIsMediaExpanding) { scaleMinSize } else { scaleMaxSize }
                    constraintSet?.let {
                        it.getConstraint(R.id.ivMin1).layout.apply {
                            mHeight = finalSize
                            mWidth = finalSize
                        }
                        it.applyTo(vTranslationY)
                    }

                    /*val lp = ivMin1.layoutParams
                    if (mIsMediaExpanding) {
                        lp.height = scaleMinSize
                        lp.width = scaleMinSize
                    } else {
                        lp.height = scaleMaxSize
                        lp.width = scaleMaxSize
                    }
                    ivMin1.layoutParams = lp*/
                }
            }
        }else{
            scaleAnimation?.spring?.setFinalPosition(if (toExpand) scaleMinSize.toFloat() else scaleMaxSize.toFloat())
        }
        scaleAnimation?.start()
    }


    fun getAlphaAnimatorX(toExpand: Boolean): ValueAnimator {

        if (alphaAnimatorX1 == null){
            alphaAnimatorX1 = ValueAnimator.ofFloat().apply {
                setFloatValues(1f, 0f)
                setDuration(150)
                interpolator = FastOutSlowInInterpolator()

                addUpdateListener {
                    val alpha = it.getAnimatedValue() as Float
                    constraintSet?.let {
                        it.getConstraint(R.id.ivMin1).propertySet.alpha = alpha
                        it.getConstraint(R.id.ivMin2).propertySet.alpha = alpha
                        it.applyTo(vTranslationY)
                    }

                    /*val alpha = it.getAnimatedValue() as Float
                    ivMin1.alpha = alpha
                    ivMin2.alpha = alpha*/
                    //Log.d("MainActivity_","min alpha = "+alpha)
                }

                addListener(object :
                    Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        val alpha = if (mIsMediaExpanding) { 0f } else { 1f }
                        constraintSet?.let {
                            it.getConstraint(R.id.ivMin1).propertySet.alpha = alpha
                            it.getConstraint(R.id.ivMin2).propertySet.alpha = alpha
                            it.applyTo(vTranslationY)
                        }

                        /*if (mIsMediaExpanding) {
                            ivMin1.alpha = 0f
                            ivMin2.alpha = 0f
                            //Log.d("MainActivity_","set min alpha = 0")
                        } else {
                            ivMin1.alpha = 1f
                            ivMin2.alpha = 1f
                            //Log.d("MainActivity_","set min alpha = 1")
                        }*/
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })
            }
        }else{
            alphaAnimatorX1!!.setValues(
                PropertyValuesHolder.ofFloat("", ivMin1.alpha,
                    if (toExpand) 0f else 1f)
            )
        }
        return alphaAnimatorX1!!
    }


    fun getAlphaAnimatorD1(toExpand: Boolean): ValueAnimator {

        //c.getConstraint().propertySet.alpha

        if (alphaAnimatorD1 == null){
            alphaAnimatorD1 = ValueAnimator.ofFloat().apply {
                setFloatValues(0f, 1f)
                setDuration(300)
                interpolator = FastOutSlowInInterpolator()

                addUpdateListener {
                    val alpha = it.getAnimatedValue() as Float
                    constraintSet?.let {
                        it.getConstraint(R.id.ivBig1).propertySet.alpha = alpha
                        it.getConstraint(R.id.ivBig2).propertySet.alpha = if (!mIsMediaExpanding) 0f else alpha
                        it.applyTo(vTranslationY)
                    }


                    /*val alpha = it.getAnimatedValue() as Float
                    //Log.d("MainActivity_","d1 alpha = "+alpha)
                    ivBig1.alpha = alpha
                    constraintSet?.setAlpha(R.id.ivBig1,alpha)
                    //constraintSet?.applyTo(vTranslationY)
                    if (!mIsMediaExpanding){
                        //ivBig1.alpha = 0f
                        ivBig2.alpha = 0f
                        return@addUpdateListener
                    }
                    ivBig2.alpha = alpha*/
                }

                addListener(object :
                    Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        val finalAlpha = if (mIsMediaExpanding) { 1f } else { 0f }
                        constraintSet?.let {
                            it.getConstraint(R.id.ivBig1).propertySet.alpha = finalAlpha
                            it.getConstraint(R.id.ivBig2).propertySet.alpha = finalAlpha
                            it.applyTo(vTranslationY)
                        }

                        /*if (mIsMediaExpanding) {
                            ivBig1.alpha = 1f
                            //constraintSet 会和自带的属性相互影响，最好只设置一种来改变属性。
                            //constraintSet?.setAlpha(R.id.ivBig1,1f)
                            ivBig2.alpha = 1f
                            Log.d("MainActivity_","d1 alpha = 1f")
                        } else {
                            ivBig1.alpha = 0f
                            //constraintSet?.setAlpha(R.id.ivBig1,0f)
                            ivBig2.alpha = 0f
                            Log.d("MainActivity_","d1 alpha = 0f")
                        }*/
                        //constraintSet?.applyTo(vTranslationY)
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })
            }
        }else{
            alphaAnimatorD1!!.setValues(
                PropertyValuesHolder.ofFloat("", if (toExpand) 0f else 1f,
                    if (toExpand) 1f else 0f)
            )
        }
        return alphaAnimatorD1!!
    }


    fun getAlphaAnimatorD2(toExpand: Boolean): ValueAnimator {

        if (alphaAnimatorD2 == null){
            alphaAnimatorD2 = ValueAnimator.ofFloat().apply {
                setFloatValues(0f, 1f)
                duration = 300
                startDelay = 200
                interpolator = FastOutSlowInInterpolator()

                addUpdateListener { value->

                    constraintSet?.let {
                        it.getConstraint(R.id.llAction).propertySet.alpha = if (!mIsMediaExpanding) 0f else (value.animatedValue as Float)
                        it.applyTo(vTranslationY)
                    }


                   /* if (!mIsMediaExpanding){
                        llAction.alpha = 0f
                        return@addUpdateListener
                    }
                    val alpha = value.getAnimatedValue() as Float
                    llAction.alpha = alpha*/
                }

                addListener(object :
                    Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationEnd(animation: Animator) {
                        constraintSet?.let {
                            it.getConstraint(R.id.llAction).propertySet.alpha = if (mIsMediaExpanding) 1f else 0f
                            it.applyTo(vTranslationY)
                        }

                        /*if (mIsMediaExpanding) {
                            llAction.alpha = 1f
                        } else {
                            llAction.alpha = 0f
                        }*/
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })
            }
        }else{
            alphaAnimatorD2!!.startDelay = if (toExpand) 200 else 0
            alphaAnimatorD2!!.setValues(
                PropertyValuesHolder.ofFloat("", llAction.alpha,
                    if (toExpand) 1f else 0f)
            )
        }
        return alphaAnimatorD2!!
    }

    /**
     * @param toExpand :是否去展开 true：去展开
     */
    private fun refreshAnimate(toExpand: Boolean) {
        mIsMediaExpanding = toExpand
        getAnimate(toExpand)
        scaleMinIcon(toExpand)
        //getAnimate(toExpand).start()
        //valueAnimator!!.removeAllListeners()
        //valueAnimator!!.cancel()
        getAlphaAnimatorX(toExpand).start()
        getAlphaAnimatorD1(toExpand).start()
        getAlphaAnimatorD2(toExpand).start()
    }


    private fun refreshViewAnimate(toExpand: Boolean){
        mIsViewExpanding = toExpand
        setViewAnimate(toExpand)
    }
    private var mViewBgAnimator:ValueAnimator?=null
    private fun setViewAnimate(toExpand: Boolean){
        if (mViewBgAnimator == null) {
            mViewBgAnimator = ValueAnimator.ofInt().apply {
                setIntValues(minHeight, maxHeight)
                duration = 1500
                addUpdateListener {
                    val lp = mViewBg.layoutParams
                    lp.height = it.animatedValue as Int
                    mViewBg.layoutParams = lp
                }

                addListener(object :
                    Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        Log.d("MainActivity_", "onAnimationStart: ")
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        //animation.is()
                        Log.d("MainActivity_", "onAnimationEnd: ")
                        val lp = mViewBg.layoutParams
                        if (mIsViewExpanding) {
                            lp.height = maxHeight
                        } else {
                            lp.height = minHeight
                        }
                        mViewBg.layoutParams = lp
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        Log.d("MainActivity_", "onAnimationCancel: ")
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        Log.d("MainActivity_", "onAnimationRepeat: ")
                    }
                })
            }
        } /*else {
            mViewBgAnimator?.setValues(
                PropertyValuesHolder.ofInt("", if (toExpand) minHeight else maxHeight,
                    if (toExpand) maxHeight else minHeight)
            )
            Log.d("MainActivity_", "animatedFraction: "+(1 - mViewBgAnimator!!.animatedFraction))
        }*/

        //这种不适合有显示和隐藏需求的写法
        if (mViewBgAnimator!!.animatedFraction>0){
            mViewBgAnimator?.reverse()
        }else{
            mViewBgAnimator?.start()
        }

    }
}