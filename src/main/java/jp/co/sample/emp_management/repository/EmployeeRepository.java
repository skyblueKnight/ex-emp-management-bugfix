package jp.co.sample.emp_management.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.emp_management.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 * 
 */
@Repository
public class EmployeeRepository {

	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 従業員一覧情報を入社日順で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count"
				+ " FROM employees"
				+ " ORDER BY hire_date";

		List<Employee> developmentList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return developmentList;
	}

	/**
	 * 主キーから従業員情報を取得します.
	 * 
	 * @param id 検索したい従業員ID
	 * @return 検索された従業員情報
	 * @exception 従業員が存在しない場合は例外を発生します
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee development = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return development;
	}
	
	
	/**
	 * 名前のあいまい検索を行います.
	 * 入社日順で取得します。
	 * 
	 * @param name　検索を行う名前
	 * @return　取得した従業員詳細一覧
	 */
	public List<Employee> findByLikeName(String name){
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,"
				+ "characteristics,dependents_count"
				+ " FROM employees"
				+ " WHERE name LIKE :name"
				+ " ORDER BY hire_date;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", '%' + name + '%');
		List<Employee> developmentList = template.query(sql, param, EMPLOYEE_ROW_MAPPER);

		return developmentList;
	}
	
	

	/**
	 * 従業員情報を変更します.
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		String updateSql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
		template.update(updateSql, param);
	}
	
	
	
	/**
	 *従業員情報を追加します.
	 * 
	 * @param employee 追加する従業員情報
	 */
	public void insert(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		System.out.println("insert開始");
		String updateSql = "insert into employees(name,image,gender, hireDate, mailAddress,"
				+ " zipCode, address, telephone, salary, characteristics, dependentsCount)"
				+ " values(:name,:image,:gender, :hireDate, :mailAddress, :zipCode, :address,"
				+ " :telephone, :salary, :characteristics, :dependentsCount);";
		
		template.update(updateSql, param);
		System.out.println("insert終了");
	}
	
	
	/**
	 * 現在のIDの最大値を取得する.
	 * 
	 * @return　現在のIDの最大値
	 */
	public int getMaxId() {
		String sql = "SELECT MAX(id) FROM employees;";
        SqlParameterSource param = new MapSqlParameterSource();

        return template.queryForObject(sql, param,Integer.class);
		
	}
	
	
}
