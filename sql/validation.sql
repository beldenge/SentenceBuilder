--should return only 'a' and 'I'
SELECT word, part_of_speech, frequency_weight
FROM parts_of_speech
WHERE length(word) = 1 and frequency_weight > 1
order by word;

--'Hi' should be listed as an interjection
SELECT word, part_of_speech, frequency_weight
FROM parts_of_speech
where word = 'Hi';
  
--'or' should be listed as a conjunction
SELECT word, part_of_speech, frequency_weight
FROM parts_of_speech
where word = 'or';
  
--'your' should be listed as an adjective
SELECT word, part_of_speech, frequency_weight
FROM parts_of_speech
where word = 'your';