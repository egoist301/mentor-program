CREATE
OR
REPLACE FUNCTION searchPatient(text, text, text, text)
    RETURNS SETOF doctor AS
    $$
SELECT DISTINCT doctor.id,
                first_name,
                last_name,
                middle_name,
                phone_number,
                date_of_birth,
                price_per_consultation,
                identification_number,
                doctor.create_date,
                doctor.update_date
FROM doctor
         LEFT JOIN doctor_illness ON doctor_id = doctor.id
         LEFT JOIN illness ON illness_id = illness.id
WHERE CASE
          WHEN $1 IS NOT NULL THEN first_name LIKE CONCAT('%', $1, '%')
          ELSE first_name IS NOT NULL END
  AND CASE
          WHEN $2 IS NOT NULL THEN last_name LIKE CONCAT('%', $2, '%')
          ELSE last_name IS NOT NULL END
  AND CASE
          WHEN $3 IS NOT NULL THEN middle_name LIKE CONCAT('%', $3, '%')
          ELSE middle_name IS NOT NULL END
  AND CASE
          WHEN $4 IS NOT NULL THEN
              name LIKE CONCAT('%', $4, '%')
          ELSE name IS NULL OR name IS NOT NULL
    END
    $$ LANGUAGE SQL;