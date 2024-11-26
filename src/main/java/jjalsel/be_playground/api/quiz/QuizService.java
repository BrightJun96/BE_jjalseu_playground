package jjalsel.be_playground.api.quiz;

import jjalsel.be_playground.api.quiz.dto.request.*;
import jjalsel.be_playground.api.quiz.dto.response.MultipleChoiceResponse;
import jjalsel.be_playground.api.quiz.dto.response.QuizCheckResponse;
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
       퀴즈 목록 (조건에 맞는 랜덤 10개)
     */
    public QuizResponseWithTotalTime getQuizList(QuizListRequest quizListRequest) {
        List<QuizEntity> quizzes = quizRepository.findFilteredAndRandom(
                quizListRequest
        );

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

    // 퀴즈 PK 목록
    public List<Long> getQuizPkList(QuizListRequest quizListRequest) {
        return quizRepository.findFilteredAndRandom(quizListRequest)
                .stream()
                .map(QuizEntity::getId)
                .toList();
    }

    // 퀴즈 detailURL 목록
    public List<String> getQuizDetailUrlList(QuizListRequest quizListRequest) {
        return quizRepository.findFilteredAndRandom(quizListRequest)
                .stream()
                .map(QuizEntity::getDetailUrl)
                .toList();
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


    // 퀴즈 상세 조회(퀴즈 ID로 조회)
    public QuizResponse getQuizById(Long quizId) {
        // QuizEntity 조회
        QuizEntity quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 퀴즈가 존재하지 않습니다."));

        // MultipleChoiceEntity 조회
        List<MultipleChoiceResponse> multipleChoices = multipleChoiceRepository.findByQuizId(quiz.getId())
                .stream()
                .map(entity -> MultipleChoiceResponse.fromEntity((MultipleChoiceEntity) entity))
                .toList();

        // QuizResponse로 변환
        return QuizResponse.fromEntity(quiz, multipleChoices);
    }

    // 퀴즈 상세 조회(퀴즈 detailURL로 조회)
    public QuizResponse getQuizByDetailUrl(String detailUrl) {
        // QuizEntity 조회
        QuizEntity quiz = quizRepository.findByDetailUrl(detailUrl);
//                .orElseThrow(() -> new IllegalArgumentException("해당 URL의 퀴즈가 존재하지 않습니다."));

        // MultipleChoiceEntity 조회
        List<MultipleChoiceResponse> multipleChoices = multipleChoiceRepository.findByQuizId(quiz.getId())
                .stream()
                .map(entity -> MultipleChoiceResponse.fromEntity((MultipleChoiceEntity) entity))
                .toList();

        // QuizResponse로 변환
        return QuizResponse.fromEntity(quiz, multipleChoices);
    }

    // 퀴즈 정답 확인
    public QuizCheckResponse checkAnswer(QuizCheckRequest quizCheckRequest) {

        QuizEntity quiz  = quizRepository.findById(quizCheckRequest.getQuizId())
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 퀴즈가 존재하지 않습니다."));



        /*
          정답과 비교하여 결과 반환
           correctAnswers 내부의 넘버와 quizCheckRequest.answer의 넘버를 비교하여
           quizCheckRequest.answer에 correctAnswers값들이 포함되어있지 않다면 오답
          */

        boolean isCorrect = true;
        // 모든 correctAnswers 값이 userAnswers에 포함되어 있는지 확인
        for (int correctAnswer : quiz.getMultipleChoiceAnswer()) {
            if (!quizCheckRequest.getUserAnswer().contains(correctAnswer)) {
                isCorrect = false;
                break;
            }
        }



        return QuizCheckResponse.builder()
                .isCorrect(isCorrect)
                .answer(quiz.getMultipleChoiceAnswer())
                .userAnswer(quizCheckRequest.getUserAnswer())
                .build();


    };



    // 퀴즈 삭제
    public void deleteQuiz(Long quizId) {
        quizRepository.deleteById(quizId);
    }

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



    // 퀴즈 수정
    public void partialUpdateQuiz(Long quizId, QuizUpdateRequest quizUpdateRequest) {
        // 기존 퀴즈 조회
        QuizEntity quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 퀴즈가 존재하지 않습니다."));


        quiz.updateFields(
                quizUpdateRequest.getTitle(),
                quizUpdateRequest.getContent(),
                quizUpdateRequest.getSubjectAnswer(),
                quizUpdateRequest.getMultipleChoiceAnswer(),
                quizUpdateRequest.getType(),
                quizUpdateRequest.getHint(),
                quizUpdateRequest.getExplanation(),
                quizUpdateRequest.getField(),
                quizUpdateRequest.getLang(),
                quizUpdateRequest.getTime(),
                quizUpdateRequest.getLevel(),
                quizUpdateRequest.getIsMultiple()
        );

        // QuizEntity 저장
        quizRepository.save(quiz);
    }

}
