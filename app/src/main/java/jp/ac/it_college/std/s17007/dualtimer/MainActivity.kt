package jp.ac.it_college.std.s17007.dualtimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val handler = Handler()
    var timeValLeft = 0
    var timeValRight = 0
    var timeValTotal = 0
    var flagL = 0
    var flagR = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val runnableL = object : Runnable{
            override fun run() {
                timeValLeft++
                timeValTotal = calcTotalTime()

                timeToText(timeValLeft)?.let{
                    timeLeft.text = it
                }
                timeToText(timeValTotal)?.let{
                    TotalTime.text = it
                }

                handler.postDelayed(this, 1000)
            }
        }

        val runnableR = object : Runnable{
            override fun run() {
                timeValRight++
                timeValTotal = calcTotalTime()

                timeToText(timeValRight)?.let{
                    timeRight.text = it
                }
                timeToText(timeValTotal)?.let{
                    TotalTime.text = it
                }

                handler.postDelayed(this, 1000)
            }
        }

        buttonLeft.setOnClickListener{//左ボタン
            if(flagL == 0 && flagR == 0){//片方ずつ稼働させる
                handler.post(runnableL)
                flagL = 1
            }else{
                handler.removeCallbacks(runnableL)
                flagL = 0
            }
        }

        buttonRight.setOnClickListener{//右ボタン
            if(flagR == 0 && flagL == 0){//片方ずつ稼働させる
                handler.post(runnableR)
                flagR = 1
            }else{
                handler.removeCallbacks(runnableR)
                flagR = 0
            }
        }

        reset.setOnClickListener{//リセットボタン
            handler.removeCallbacks(runnableL)
            handler.removeCallbacks(runnableR)
            timeValLeft = 0
            timeValRight = 0
            timeValTotal = 0
            flagL = 0
            flagR = 0

            timeToText(timeValLeft)?.let{
                timeLeft.text = it
            }
            timeToText(timeValRight)?.let{
                timeRight.text = it
            }
            timeToText(timeValTotal)?.let{
                TotalTime.text = it
            }
        }
    }


    private fun calcTotalTime(): Int {
        return timeValLeft + timeValRight//timeValTotal = timeValLeft + timeValRight
    }


    private fun timeToText(time: Int = 0): String? {//表示用
        return if(time < 0){
            null
        } else if(time == 0){
            "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60

            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }
}
