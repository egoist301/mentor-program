CREATE OR REPLACE FUNCTION searchPatient(text, text, text, text, text)
    RETURNS SETOF patients AS
$$
SELECT patients.id, first_name, last_name, middle_name, phone_number, date_of_birth
FROM patients JOIN patients_illness ON patient_id = patients.id JOIN illness ON illness_id = illness.id
WHERE first_name LIKE CONCAT('%', $1, '%')
  AND last_name LIKE CONCAT('%', $2, '%')
  AND middle_name LIKE CONCAT('%', $3, '%')
  AND name LIKE CONCAT('%', $4, '%')
  AND name_in_latin LIKE CONCAT('%', $5, '%');
$$ LANGUAGE SQL;
