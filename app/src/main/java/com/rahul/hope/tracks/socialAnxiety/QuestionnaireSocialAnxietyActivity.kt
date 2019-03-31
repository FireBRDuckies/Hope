package com.rahul.hope.tracks.socialAnxiety

import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.rahul.hope.R
import com.rahul.hope.fragments.QuestionFragment
import kotlinx.android.synthetic.main.activity_questionnaire_social_anxiety.*
import org.jetbrains.anko.alert


class QuestionnaireSocialAnxietyActivity : AppCompatActivity() {

    private var currentQuestion: Int = 0
    private var questionKey: String = "question"
    private lateinit var scores: ArrayList<Int>
    private lateinit var responseSeekBar: SeekBar
    private lateinit var selectedValueText: TextView

    private var mCurrentUser: FirebaseUser? = null
    private lateinit var mFirebaseDb: FirebaseDatabase

    val questions = arrayOf(
        "felt moments of sudden terror, fear, or " +
                "fright in social situations", "felt anxious, worried, or nervous about " +
                "social situations", "had thoughts of being rejected, humiliated, " +
                "embarrassed, ridiculed, or offending others", "felt a racing heart, sweaty, trouble " +
                "breathing, faint, or shaky in social " +
                "situations", "felt tense muscles, felt on edge or restless, " +
                "or had trouble relaxing in social situations", "avoided, or did not approach or enter, " +
                "social situations", "left social situations early or participated " +
                "only minimally (e.g., said little, avoided eye " +
                "contact)", "spent a lot of time preparing what to say or " +
                "how to act in social situations", "distracted myself to avoid thinking about " +
                "social situations", "needed help to cope with social situations " +
                "(e.g., alcohol or medications, superstitious " +
                "objects)"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.rahul.hope.R.layout.activity_questionnaire_social_anxiety)
        responseSeekBar = findViewById(R.id.responseSeekBar)
        selectedValueText = findViewById(R.id.selectedValueTextAnxiety)

        mCurrentUser = FirebaseAuth.getInstance().currentUser
        mFirebaseDb = FirebaseDatabase.getInstance()
        scores = ArrayList(questions.size)

        socialAnxietyViewPager.adapter = SocialAnxietyPagerAdapter(supportFragmentManager)
        socialAnxietyViewPager.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        responseSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedValueText.text = when (progress) {
                    0 -> "Never"
                    1 -> "Occasionally"
                    2 -> "Half of the time"
                    3 -> "Most of the time"
                    4 -> "All of the time"
                    else -> "Never"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        nextButton.setOnClickListener {
            if (++currentQuestion < questions.size) {
                socialAnxietyViewPager.currentItem = currentQuestion
                scores.add(responseSeekBar.progress)
                responseSeekBar.progress = 0
            } else if (currentQuestion == 10) {
                val anxietyScoresRef = mFirebaseDb.getReference("users")
                anxietyScoresRef.child(mCurrentUser!!.uid).child("anxietyScores")
                    .child(System.currentTimeMillis().toString()).setValue(getUserAnxietyScore())
                alert("Depression Level: ${(getUserAnxietyScore()*100)/40}%").show()
                Handler().postDelayed({ finish() }, 5000)
            }
            if (socialAnxietyViewPager.currentItem + 1 == questions.size) {
                nextButton.text = "Done"
            }
        }
    }

    private fun getUserAnxietyScore(): Int {
        var sum = 0
        scores.forEach {
            sum += it
        }
        return sum
    }

    inner class SocialAnxietyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            val questionFragment: Fragment = QuestionFragment()
            val bundle = Bundle()
            bundle.putString(questionKey, questions[position])
            questionFragment.arguments = bundle
            return questionFragment
        }

        override fun getCount(): Int {
            return questions.size
        }
    }
}
