package io.viewpoint.widget.samples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.viewpoint.widget.marquee.startMarqueeAnimation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            marqueeTextView.startMarqueeAnimation(
                delayMilliseconds = 500L
            )
        }
    }
}
