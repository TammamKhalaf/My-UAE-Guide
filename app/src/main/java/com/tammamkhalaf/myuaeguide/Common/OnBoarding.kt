package com.tammamkhalaf.myuaeguide.Common

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.tammamkhalaf.myuaeguide.Common.OnBoarding
import com.tammamkhalaf.myuaeguide.HelperClasses.SliderAdapter
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.User.UserDashboard

class OnBoarding : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    var dots_layout: LinearLayout? = null
    var sliderAdapter: SliderAdapter? = null
    lateinit var dots: Array<TextView?>
    var letsGetsStarted: Button? = null
    var animation: Animation? = null
    var currentPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        viewPager = findViewById(R.id.slider)
        dots_layout = findViewById(R.id.dots)
        letsGetsStarted = findViewById(R.id.get_started_btn)
        sliderAdapter = SliderAdapter(this)
        viewPager.setAdapter(sliderAdapter)
        addDots(0)
        viewPager.addOnPageChangeListener(changeListener)
    }

    private fun addDots(position: Int) {
        dots = arrayOfNulls(4)
        dots_layout!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226")
            dots[i]!!.textSize = 35f
            dots_layout!!.addView(dots[i])
        }
        if (dots.size > 0) {
            dots[position]!!.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        }
    }

    var changeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {
            addDots(position)
            currentPosition = position
            if (position == 0) {
                letsGetsStarted!!.visibility = View.INVISIBLE
            } else if (position == 1) {
                letsGetsStarted!!.visibility = View.INVISIBLE
            } else if (position == 2) {
                letsGetsStarted!!.visibility = View.INVISIBLE
            } else {
                animation = AnimationUtils.loadAnimation(this@OnBoarding, R.anim.bottom_anim)
                letsGetsStarted!!.animation = animation
                letsGetsStarted!!.visibility = View.VISIBLE
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    fun skip(view: View?) {
        startActivity(Intent(this, UserDashboard::class.java))
        finish()
    }

    fun next(view: View?) {
        viewPager!!.currentItem = currentPosition + 1
    }
}