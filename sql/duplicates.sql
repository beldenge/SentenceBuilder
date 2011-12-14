--This query finds duplicates where there is a space in the word somewhere
SELECT p1.*, p2.*
FROM parts_of_speech p1
	inner join parts_of_speech p2
		on p1.word = replace(p2.word, ' ', '')
WHERE p1.word != p2.word
	AND p1.part_of_speech=p2.part_of_speech