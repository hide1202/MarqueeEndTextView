package io.viewpoint.widget.samples

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.viewpoint.widget.marquee.awaitMarqueeAnimation
import io.viewpoint.widget.marquee.observeMarqueeAnimation
import io.viewpoint.widget.marquee.startMarqueeAnimationAsync
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun showCompleteToast() {
            Toast.makeText(this, "Complete animation!", Toast.LENGTH_SHORT).show()
        }

        startWithCompletableButton.setOnClickListener {
            marqueeTextView.observeMarqueeAnimation(
                delayMilliseconds = 500L,
                durationMilliseconds = 1000L
            ).subscribe({
                showCompleteToast()
            }, { /* do nothing*/ })
        }

        startAsyncButton.setOnClickListener {
            marqueeTextView.startMarqueeAnimationAsync(
                delayMilliseconds = 500L,
                durationMilliseconds = 1000L
            ) {
                showCompleteToast()
            }
        }

        startCoroutineButton.setOnClickListener {
            val deferred = marqueeTextView.awaitMarqueeAnimation(
                delayMilliseconds = 500L,
                durationMilliseconds = 1000L
            )
            CoroutineScope(Dispatchers.Main).launch {
                deferred.await()
                showCompleteToast()
            }
        }
    }
}
