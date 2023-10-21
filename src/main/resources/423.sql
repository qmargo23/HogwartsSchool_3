select s.id as student_id, f.id  as faculty_id ,*
from student s
join faculty f ON s.faculty_id = f.id;

select s.*
from student s
join avatar a on s.id = a.student_id;