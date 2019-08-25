package io.viewpoint.widget.samples

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.viewpoint.widget.marquee.awaitStartMarqueeAnimation
import io.viewpoint.widget.marquee.startMarqueeAnimationAsync
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun showCompleteToast() {
            Toast.makeText(this, "Complete animation!", Toast.LENGTH_SHORT).show()
        }

        startWithCompletableButton.setOnClickListener {
            marqueeTextView.awaitStartMarqueeAnimation(
                delayMilliseconds = 500L
            ).subscribe({
                showCompleteToast()
            }, { /* do nothing*/ })
        }

        startAsyncButton.setOnClickListener {
            marqueeTextView.startMarqueeAnimationAsync(
                delayMilliseconds = 500L
            ) {
                showCompleteToast()
            }
        }
    }
}
