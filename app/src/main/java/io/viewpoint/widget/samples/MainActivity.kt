package io.viewpoint.widget.samples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.viewpoint.widget.marquee.toMarqueeAnimation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            val marqueeAnimation = marqueeTextView.toMarqueeAnimation()
            marqueeAnimation.startAnimation()
        }
    }
}
