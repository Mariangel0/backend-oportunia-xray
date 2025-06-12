-- Ajusta todas las secuencias para continuar correctamente desde MAX(id) + 1

-- User
ALTER SEQUENCE user_seq INCREMENT BY 1;
SELECT setval('user_seq', GREATEST((SELECT MAX(id) FROM users), 0) + 1, false);

-- Company
ALTER SEQUENCE companie_seq INCREMENT BY 1;
SELECT setval('companie_seq', GREATEST((SELECT MAX(id) FROM companies), 0) + 1, false);

-- Ability
ALTER SEQUENCE ability_seq INCREMENT BY 1;
SELECT setval('ability_seq', GREATEST((SELECT MAX(id) FROM ability), 0) + 1, false);

-- Advice
ALTER SEQUENCE advice_seq INCREMENT BY 1;
SELECT setval('advice_seq', GREATEST((SELECT MAX(id) FROM advices), 0) + 1, false);

-- Company Review
ALTER SEQUENCE company_review_seq INCREMENT BY 1;
SELECT setval('company_review_seq', GREATEST((SELECT MAX(id) FROM companies_reviews), 0) + 1, false);

-- Curriculum
ALTER SEQUENCE curriculums_seq INCREMENT BY 1;
SELECT setval('curriculums_seq', GREATEST((SELECT MAX(id) FROM curriculums), 0) + 1, false);

-- Education
ALTER SEQUENCE education_seq INCREMENT BY 1;
SELECT setval('education_seq', GREATEST((SELECT MAX(id) FROM education), 0) + 1, false);

-- Experience
ALTER SEQUENCE experience_seq INCREMENT BY 1;
SELECT setval('experience_seq', GREATEST((SELECT MAX(id) FROM experiences), 0) + 1, false);

-- Interview
ALTER SEQUENCE interview_seq INCREMENT BY 1;
SELECT setval('interview_seq', GREATEST((SELECT MAX(id) FROM interview), 0) + 1, false);

-- IAAnalysis
ALTER SEQUENCE analysis_seq INCREMENT BY 1;
SELECT setval('analysis_seq', GREATEST((SELECT MAX(id) FROM ia_analysis), 0) + 1, false);

-- Quiz
ALTER SEQUENCE quiz_seq INCREMENT BY 1;
SELECT setval('quiz_seq', GREATEST((SELECT MAX(id) FROM quizzes), 0) + 1, false);

-- Streak
ALTER SEQUENCE streak_seq INCREMENT BY 1;
SELECT setval('streak_seq', GREATEST((SELECT MAX(id) FROM streaks), 0) + 1, false);
