-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 단 조회결과는 급여의 내림차순으로 정렬되어 나타나야 합니다. 

-- 문제1.
-- 현재 전체 사원의 평균 급여보다 많은 급여를 받는 사원은 몇 명이나 있습니까?
select count(*) as 'count'
from salaries
where to_date = '9999-01-01' and salary > (
	-- 전체 사원의 평균 급여 구하기
	select avg(salary)
	from salaries
	where to_date = '9999-01-01'
);

-- 문제2. 
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서, 급여을 조회하세요. 단 조회결과는 급여의 내림차순으로 정렬합니다.
select e.emp_no, e.first_name, e.last_name, d.dept_name, s.salary
from employees e
join salaries s on e.emp_no = s.emp_no and s.to_date = '9999-01-01'
join dept_emp de on e.emp_no = de.emp_no and de.to_date = '9999-01-01'
join departments d on de.dept_no = d.dept_no
where (de.dept_no, s.salary) in (
	-- 각 부서별 최고의 급여
	select de.dept_no, max(s.salary)
	from salaries s
	join dept_emp de on s.emp_no = de.emp_no and de.to_date = '9999-01-01'
	group by de.dept_no
)
order by s.salary desc;

-- 문제3.
-- 현재, 자신들의 부서의 평균급여보다 급여가 많은 사원들의 사번, 이름 그리고 급여를 조회하세요.
with dept_avg as (
    -- 각 부서별 평균 급여
    select de.dept_no, avg(s.salary) as avg_salary
    from salaries s
    join dept_emp de on s.emp_no = de.emp_no
    where de.to_date = '9999-01-01'
    group by de.dept_no
)

select e.emp_no, e.first_name, e.last_name, s.salary
from employees e
join salaries s on e.emp_no = s.emp_no and s.to_date = '9999-01-01'
join dept_emp de on e.emp_no = de.emp_no and de.to_date = '9999-01-01'
join dept_avg da on de.dept_no = da.dept_no
where s.salary > da.avg_salary
order by s.salary desc;

-- 문제4.
-- 현재, 사원들의 사번, 이름, 그리고 매니저 이름과 부서 이름을 출력해 보세요.
select e.emp_no, e.first_name, e.last_name, e2.first_name 'manager_first_name', e2.last_name 'manager_last_name', d.dept_name
from employees e
join dept_emp de on e.emp_no = de.emp_no and de.to_date = '9999-01-01'
join departments d on de.dept_no = d.dept_no
join dept_manager dm on de.dept_no = dm.dept_no and dm.to_date = '9999-01-01'
join employees e2 on e2.emp_no = dm.emp_no;

-- 문제5.
-- 현재, 평균급여가 가장 높은 부서의 사원들의 사번, 이름, 직책 그리고 급여를 조회하고 급여 순으로 출력하세요.
select e.emp_no, e.first_name, e.last_name, t.title, s.salary
from employees e
join salaries s on e.emp_no = s.emp_no and s.to_date = '9999-01-01'
join dept_emp de on e.emp_no = de.emp_no and de.to_date = '9999-01-01'
join titles t on e.emp_no = t.emp_no and de.to_date = '9999-01-01'
where de.dept_no = (
	select de2.dept_no
	from salaries s2
	join dept_emp de2 on s2.emp_no = de2.emp_no
	where de2.to_date = '9999-01-01'
	group by de2.dept_no
	order by avg(s2.salary) desc
	limit 1
)
order by s.salary desc;

-- 문제6.
-- 현재, 평균 급여가 가장 높은 부서의 이름 그리고 평균급여를 출력하세요.
with dept_avg as (
    -- 각 부서별 평균 급여
    select de.dept_no, avg(s.salary) as avg_salary
    from salaries s
    join dept_emp de on s.emp_no = de.emp_no
    where de.to_date = '9999-01-01'
    group by de.dept_no
)

select dept_name, avg_salary
from dept_avg da
join departments d on da.dept_no = d.dept_no
order by da.avg_salary desc
limit 1;

-- 문제7.
-- 현재, 평균 급여가 가장 높은 직책의 타이틀 그리고 평균급여를 출력하세요.
select t.title, avg(s.salary) as '평균급여'
from titles t
join salaries s on s.emp_no = t.emp_no
where s.to_date = '9999-01-01'
group by t.title
order by avg(s.salary) desc;