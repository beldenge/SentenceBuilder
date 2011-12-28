--A lot of the words less than 3 characters long have a high frequency even though they are never ever used in the English language.
SELECT word, part_of_speech, frequency_weight
  FROM parts_of_speech
  where length(word)<3 and frequency_weight>1
  order by frequency_weight desc;

--'Hi' is not listed as an interjection, but it probably should be.
SELECT word, part_of_speech, frequency_weight
  FROM parts_of_speech
  where word = 'Hi';
  
--'or' is not listed as a conjunction, but it definitely should be.
SELECT word, part_of_speech, frequency_weight
  FROM parts_of_speech
  where word = 'or';
  
--'your' is not listed as an adjective, but it definitely should be.
SELECT word, part_of_speech, frequency_weight
  FROM parts_of_speech
  where word = 'yoor';