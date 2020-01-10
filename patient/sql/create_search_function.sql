CREATE FUNCTION searchPatient(text, text, text, int, date)
    RETURNS patients AS
$$
SELECT id, first_name, last_name, middle_name, phone_number, date_of_birth
FROM patients
WHERE first_name LIKE $1
  AND last_name LIKE $2
  AND middle_name LIKE $3
  AND phone_number = $4
  AND date_of_birth = $5;
$$ LANGUAGE SQL;

SELECT first_name, last_name, middle_name, phone_number, date_of_birth
FROM searchPatient('%', '%', '%', 3221114,
    '2000-08-27');