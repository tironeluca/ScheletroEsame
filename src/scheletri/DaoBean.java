package scheletri;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.DBConnect;
import it.polito.tdp.food.db.Food;

public class DaoBean {
	
	public List<Food> listAllFood(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiment(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getInt("condiment_portion_code"),
							res.getDouble("condiment_grains"),
							res.getDouble("condiment_whole_grains"),
							res.getDouble("condiment_vegetables"),
							res.getDouble("condiment_dkgreen"),
							res.getDouble("condiment_orange"),
							res.getDouble("condiment_starchy_vegetables"),
							res.getDouble("condiment_other_vegetables"),
							res.getDouble("condiment_fruits"),
							res.getDouble("condiment_milk"),
							res.getDouble("condiment_meat"),
							res.getDouble("condiment_soy"),
							res.getDouble("condiment_drybeans_peas"),
							res.getDouble("condiment_oils"),
							res.getDouble("condiment_solid_fats"),
							res.getDouble("condiment_added_sugars"),
							res.getDouble("condiment_alcohol"),
							res.getDouble("condiment_calories"),							
							res.getDouble("condiment_saturated_fats")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	
	public List<Portion> listAllPortion(){
		String sql = "SELECT * FROM portion " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion (res.getInt("portion_id"),
							res.getInt("food_code"),
							res.getInt("portion_default"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("factor"),
							res.getDouble("increment"),
							res.getDouble("multiplier"),
							res.getDouble("grains"),
							res.getDouble("whole_grains"),
							res.getDouble("vegetables"),
							res.getDouble("orange_vegetables"),
							res.getDouble("drkgreen_vegetables"),
							res.getDouble("starchy_vegetables"),
							res.getDouble("other_vegetables"),
							res.getDouble("fruits"),
							res.getDouble("milk"),
							res.getDouble("meats"),
							res.getDouble("soy"),
							res.getDouble("drybeans_peas"),
							res.getDouble("oils"),
							res.getDouble("solid_fats"),							
							res.getDouble("added_sugars"),
							res.getDouble("alcohol"),
							res.getDouble("calories"),
							res.getDouble("saturated_fats")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}


}
