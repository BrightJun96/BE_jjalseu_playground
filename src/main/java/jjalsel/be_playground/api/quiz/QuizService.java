package jjalsel.be_playground.api.quiz;

import jjalsel.be_playground.api.quiz.dto.request.QuizItemRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizListRequest;
import jjalsel.be_playground.api.quiz.dto.request.QuizRequest;
import jjalsel.be_playground.api.quiz.dto.response.MultipleChoiceResponse;
import jjalsel.be_playground.api.quiz.dto.response.QuizResponse;
import jjalsel.be_playground.api.quiz.dto.response.QuizResponseWithTotalTime;
import jjalsel.be_playground.persistence.multipleChoice.MultipleChoiceEntity;
import jjalsel.be_playground.persistence.multipleChoice.MultipleChoiceRepository;
import jjalsel.be_playground.persistence.quiz.QuizEntity;
import jjalsel.be_playground.persistence.quiz.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final MultipleChoiceRepository multipleChoiceRepository;

    /*
      퀴즈 등록
     */
    public void registerQuiz(QuizRequest quizRequest) {



        // 1. QuizRequest 객체를 QuizEntity로 변환 및 저장 (multipleChoiceContents 제외)
        QuizEntity savedQuiz = quizRepository.save(quizRequest.toQuizEntity());

        // 2. multipleChoiceContents가 있는 경우 처리
        if (quizRequest.getMultipleChoiceContents() != null) {
            int choiceNumber = 1;

            for (String choiceContent : quizRequest.getMultipleChoiceContents()) {
                // MultipleChoiceEntity 생성
                MultipleChoiceEntity multipleChoice = MultipleChoiceEntity.builder()
                        .content(choiceContent)  // 선택지 내용
                        .quiz(savedQuiz)// 저장된 퀴즈 엔티티와 연관
                        .number(choiceNumber)  // 선택지 번호
                        .build();

                // MultipleChoiceRepository에 저장
                multipleChoiceRepository.save(multipleChoice);

                choiceNumber++;
            }
        }
    }


   /*
       퀴즈 목록 (조건에 맞는 랜덤 10개)
     */
    public QuizResponseWithTotalTime getQuizList(QuizListRequest quizListRequest) {
        List<QuizEntity> quizzes = quizRepository.findFilteredAndRandom(
                quizListRequest
        );

        // 각 퀴즈를 QuizResponse로 변환
        // 각 퀴즈를 QuizResponse로 변환
        List<QuizResponse> quizResponses = quizzes.stream()
                .map(quiz -> {
                    List<MultipleChoiceResponse> multipleChoices = multipleChoiceRepository.findByQuizId(quiz.getId())
                            .stream()
                            .map(entity -> MultipleChoiceResponse.fromEntity((MultipleChoiceEntity) entity))
                            .toList();

                    return QuizResponse.fromEntity(quiz, multipleChoices);
                })
                .toList();

        // time 필드 합계 계산
        Integer totalTime = quizRepository.sumOfTime(
                quizListRequest.getField(),
                quizListRequest.getLang()
        );


        // 응답 객체에 퀴즈 목록과 총 합계 시간 포함
        return QuizResponseWithTotalTime.builder()
                .quizList(quizResponses)
                .totalTime(totalTime != null ? totalTime : 0)  // null 방지
                .totalCount(quizzes.size())  // 추가
                .build();
    }


    /*
     * 퀴즈 단일 조회
     */
    public QuizResponse getQuizElement(QuizItemRequest quizItemRequest) {
        // QuizEntity 조회
        QuizEntity quiz = quizRepository.findFilteredAndRandomOne(
                quizItemRequest
        );

        // MultipleChoiceEntity 조회
        List<MultipleChoiceResponse> multipleChoices = multipleChoiceRepository.findByQuizId(quiz.getId())
                .stream()
                .map(entity -> MultipleChoiceResponse.fromEntity((MultipleChoiceEntity) entity))
                .toList();

        // QuizResponse로 변환
        return QuizResponse.fromEntity(quiz, multipleChoices);


}
}
