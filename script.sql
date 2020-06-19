---tasks
---8
-- Tables + Size MB
select owner, table_name, round((num_rows*avg_row_len)/(1024*1024)) MB from all_tables
where owner = 'EGOIST' and num_rows > 0 order by MB desc;

--Tables + Rows
select owner, table_name, num_rows from all_tables
where owner = 'EGOIST' and num_rows > 0 order by num_rows desc;

