CREATE OR REPLACE FUNCTION searchPatient(text, text, text)
    RETURNS SETOF doctor AS
$$
SELECT DISTINCT id,
                first_name,
                last_name,
                middle_name,
                phone_number,
                date_of_birth,
                price_per_consultation,
                identification_number,
                create_date,
                update_date
FROM doctor
WHERE CASE
          WHEN $1 IS NOT NULL THEN first_name ILIKE CONCAT('%', $1, '%')
          ELSE first_name IS NOT NULL END
  AND CASE
          WHEN $2 IS NOT NULL THEN last_name ILIKE CONCAT('%', $2, '%')
          ELSE last_name IS NOT NULL END
  AND CASE
          WHEN $3 IS NOT NULL THEN middle_name ILIKE CONCAT('%', $3, '%')
          ELSE middle_name IS NOT NULL END;
$$ LANGUAGE SQL;