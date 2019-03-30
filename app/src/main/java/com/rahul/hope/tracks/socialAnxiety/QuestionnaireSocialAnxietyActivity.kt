package com.rahul.hope.tracks.socialAnxiety

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rahul.hope.R
import com.rahul.hope.fragments.QuestionFragment
import kotlinx.android.synthetic.main.activity_questionnaire_social_anxiety.*

class QuestionnaireSocialAnxietyActivity : AppCompatActivity() {

    var questionKey: String = "question"

    val questions = arrayOf("felt moments of sudden terror, fear, or " +
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
            "objects)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_social_anxiety)

        socialAnxietyViewPager.adapter = SocialAnxietyPagerAdapter(supportFragmentManager)
    }

    inner class SocialAnxietyPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
        val questionFragment : Fragment = QuestionFragment()
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
