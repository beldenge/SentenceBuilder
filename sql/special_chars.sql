--This query matches all words containing special characters that are extremely unlikely to match a ciphertext
SELECT word, part_of_speech, frequency_weight
FROM parts_of_speech
WHERE word SIMILAR TO  '%[!?,"~$;/.0-9 \-'']%'