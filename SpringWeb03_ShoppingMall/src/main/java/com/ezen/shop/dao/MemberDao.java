package com.ezen.shop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ezen.shop.dto.MemberVO;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Repository
public class MemberDao {
	private JdbcTemplate template;
	@Autowired 
	public MemberDao(ComboPooledDataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	public MemberVO getMember(String id) {
		String sql= "select * from member where id=? ";
		List<MemberVO> list=null;
		
		list=template.query(sql, new RowMapper<MemberVO>() {

			@Override
			public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberVO mvo=new MemberVO();
				mvo.setId(rs.getString("id"));				
				mvo.setPwd(rs.getString("pwd"));
				mvo.setName(rs.getString("name"));		   
				mvo.setEmail(rs.getString("email"));
				mvo.setZip_num(rs.getString("zip_num"));    
				mvo.setAddress(rs.getString("address"));
				mvo.setPhone(rs.getString("phone"));		
				mvo.setUseyn(rs.getString("useyn"));
				mvo.setIndate(rs.getTimestamp("indate"));
				return mvo;
			}
			
		}, id);
		if(list.size()==0)return null; //없다면
		else return list.get(0);	
	}
	//1. template.query의 첫번째 전달인수로 sql 을 위치시킵니다
	//2. 두번째 전달인수로 new RowMapper<>(){} 를 위치시킵니다
	//3. 이때 <> 안에는 리턴형 자료의 Dto 이름을 써줍니다
	//4. 두번째 전달인수로 들어간 RowMapper 생성자를 이용하여 Override 될 메서드를 추가합니다
	//5. 추가된 메서드에 Dto객체를 생성하고, 각 멤버변수를 rs.getInt or rs.getString 등을 이용하여 set합니다
	//6. 다 채워진 Dto 객체를 리턴합니다
	//7. 마지막 괄호앞에(,)로 구분하여 ?에 들어갈 인수를 차례로 써줍니다
	//8. List가 아닌 단일 Dto 리턴되어야 한다면 list.get(0)을 리턴합니다
	//   단 리턴값이 null 경우 if로 처리합니다 
}
