CREATE OR REPLACE FUNCTION searchPatient(text, text, text, text)
    RETURNS SETOF patient AS
$$
SELECT DISTINCT patient.id,
                first_name,
                last_name,
                middle_name,
                phone_number,
                date_of_birth,
                identification_number,
                patient.create_date,
                patient.update_date
FROM patient
         LEFT JOIN patient_illness ON patient_id = patient.id
         LEFT JOIN illness ON illness_id = illness.id
WHERE first_name LIKE CONCAT('%', $1, '%')
  AND last_name LIKE CONCAT('%', $2, '%')
  AND middle_name LIKE CONCAT('%', $3, '%')
  AND CASE
          WHEN $4 != '' THEN
              name LIKE CONCAT('%', $4, '%')
          ELSE name IS NULL OR name IS NOT NULL
    END
$$ LANGUAGE SQL;

