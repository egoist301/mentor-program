CREATE OR REPLACE FUNCTION searchPatient(text, text, text)
    RETURNS SETOF patients AS
$$
SELECT id, first_name, last_name, middle_name, phone_number, date_of_birth
FROM patients
WHERE first_name LIKE CONCAT('%', $1, '%')
  AND last_name LIKE CONCAT('%', $2, '%')
  AND middle_name LIKE CONCAT('%', $3, '%');
$$ LANGUAGE SQL;
