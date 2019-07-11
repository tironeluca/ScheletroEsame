package scheletri;

public class DAO {
	
	// GESTIONE MESI
	
	public List<Month> getAllMonths() {
		final String sql = "SELECT DISTINCT MONTH " + 
				"FROM listening " + 
				"ORDER BY MONTH " ;
		
		List<Month> months = new LinkedList<Month>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				
				Month m=  Month.of(res.getInt("month"));
				
				months.add(m ) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return months ;
		
	}
	
	
	//

}
