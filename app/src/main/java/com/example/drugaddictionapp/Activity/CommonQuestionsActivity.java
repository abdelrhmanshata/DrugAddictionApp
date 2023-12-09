package com.example.drugaddictionapp.Activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drugaddictionapp.Model.Question;
import com.example.drugaddictionapp.R;
import com.example.drugaddictionapp.databinding.ActivityCommonQuestionsBinding;

import java.util.ArrayList;
import java.util.List;

public class CommonQuestionsActivity extends AppCompatActivity {

    ActivityCommonQuestionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<Question> questions = new ArrayList<>();
        questions.add(new Question("1", "ما هو إدمان المخدرات؟", "إدمان المخدرات هو حالة مزمنة تتسم بالرغبة المتكررة في استخدام المخدرات، مع العجز عن التحكم في هذا الاستخدام رغم العواقب الضارة."));
        questions.add(new Question("2", "ما هي أنواع المخدرات الشائعة التي يمكن أن تسبب الإدمان؟", "من بين المخدرات الشائعة: الحشيش، الكوكايين، الهيروين، الميثامفيتامين، والأدوية المهدئة والمسكنة."));
        questions.add(new Question("3", "ما هي العلامات والأعراض التي قد تشير إلى وجود إدمان المخدرات؟", "تتضمن العلامات: التغيير في السلوك، فقدان الاهتمام بالأمور اليومية، التعب، التغييرات الوزنية، ومشاكل في العلاقات."));
        questions.add(new Question("4", "ما هي العوامل التي تسهم في تطوير إدمان المخدرات؟", "العوامل الوراثية، والبيئية، والنفسية، والاجتماعية."));
        questions.add(new Question("5", "كيف يؤثر إدمان المخدرات على الصحة العقلية والجسدية؟", "يمكن أن يؤدي إلى مشاكل صحية عقلية وجسدية، بما في ذلك التأثير على الذاكرة والانتباه والقدرة على اتخاذ القرارات."));
        questions.add(new Question("6", "هل هناك علاجات فعّالة لإدمان المخدرات؟", "نعم، تشمل العلاجات النفسية والدوائية، بالإضافة إلى الدعم الاجتماعي والعلاج السلوكي."));
        questions.add(new Question("7", "ما هي الخطوات الأولى التي يجب أن يتخذها الشخص الذي يعتقد أنه قد أصيب بإدمان المخدرات؟", "البحث عن المساعدة الطبية، والتحدث مع أخصائي نفسي أو طبيب."));
        questions.add(new Question("8", "كيف يؤثر إدمان المخدرات على الأسرة والعلاقات الاجتماعية؟", "يمكن أن يؤدي إلى الفصل الاجتماعي والمشاكل العائلية بسبب التصرفات المتغيرة."));
        questions.add(new Question("9", "ما هو التمييز بين استخدام المخدرات الترفيهي والإدمان عليها؟", "الاستخدام الترفيهي غير المؤذي قد يكون تجربة محدودة، بينما الإدمان يتسم بفقدان السيطرة والتأثير الضار على الحياة."));
        questions.add(new Question("10", "هل هناك طرق للوقاية من إدمان المخدرات؟", "تشمل الوقاية التثقيف حول المخدرات وتعزيز الدعم الاجتماعي وتطوير مهارات التحكم في الضغوط."));

        // Adapter
        ArrayAdapter<Question> adapter = new ArrayAdapter<Question>(this, android.R.layout.simple_list_item_2, android.R.id.text1, questions) {
            @Override
            public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
                android.view.View view = super.getView(position, convertView, parent);
                // Get the item from the data source
                Question questionObj = getItem(position);

                TextView question = view.findViewById(android.R.id.text1);
                question.setText(questionObj.getQuestion());
                question.setPadding(10, 10, 10, 10);
                question.setTextColor(getResources().getColor(R.color.colorRed));

                TextView answer = view.findViewById(android.R.id.text2);
                answer.setText(questionObj.getAnswer());
                answer.setPadding(10, 10, 10, 10);

                return view;
            }
        };

        binding.listQuestions.setAdapter(adapter);

    }
}