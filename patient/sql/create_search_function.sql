CREATE OR REPLACE FUNCTION searchPatient(text, text, text)
    RETURNS SETOF patients AS
$$
SELECT id, first_name, last_name, middle_name, phone_number, date_of_birth
FROM patients
WHERE first_name LIKE CONCAT('%', $1, '%')
  AND last_name LIKE CONCAT('%', $2, '%')
  AND middle_name LIKE CONCAT('%', $3, '%');
$$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION searchIllness(text, text)
RETURNS SETOF illness AS
$$
SELECT id, name, name_in_latin, chance_to_die
FROM illness
WHERE name LIKE CONCAT('%', $1, '%')
AND name_in_latin LIKE CONCAT('%', $2, '%');
$$ LANGUAGE SQL;
