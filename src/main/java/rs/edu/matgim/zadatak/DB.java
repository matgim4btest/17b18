package rs.edu.matgim.zadatak;

import java.sql.*;

public class DB {

    String connectionString = "jdbc:sqlite:src\\main\\java\\Banka.db";

    public void notUsedRacun() {
        try ( Connection conn = DriverManager.getConnection(connectionString);  Statement s = conn.createStatement()) {

            ResultSet rs = s.executeQuery("SELECT r.IdRac\n" +"FROM Racun r, Stavka s\n" +"WHERE r.IdRac= s.IdRac\n" +"GROUP BY r.IdKom\n" +"HAVING COUNT(*)=0 ");
            while (rs.next()) {
                int IdKom = rs.getInt("IdRac");

                System.out.println(String.format("%d\t", IdKom));
            }

        } catch (SQLException ex) {
            System.out.println("Greska prilikom povezivanja na bazu");
            System.out.println(ex);
        }
    }
     public int getL() throws SQLException
    { Connection conn = DriverManager.getConnection(connectionString); 
        String upit="SELECT MAX(IdSta) FROM Stavka";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(upit);
        return(rs.getInt(1)+1);
    }
   public float zadatak(int idfil,int idrac)
   { float suma=-1;
        try{Connection conn = DriverManager.getConnection(connectionString); 
        String upit1="SELECT SUM(Stanje) FROM Racun WHERE IdFil=? ";
        PreparedStatement ps1=conn.prepareStatement(upit1);
        conn.setAutoCommit(false);
        ps1.setInt(1, idfil);
        ResultSet rs=ps1.executeQuery();
        suma=rs.getFloat(1);
        String upit2="SELECT IdRac,Stanje FROM Racun WHERE IdKom=?";
        PreparedStatement ps2=conn.prepareStatement(upit2);
        rs=ps2.executeQuery();
        while(rs.next())
        {
        int id=rs.getInt(1);
        float sum=rs.getFloat(2);
        String upit3="INSERT INTO Stavka (IdSta,Datum,Iznos,IdRac1,IdRac2) VALUES(?,'4.1.2021.',?,?,?)";
        PreparedStatement ps3=conn.prepareStatement(upit3);
        ps3.setInt(1, getL()+1);
        ps3.setFloat(2, sum);
        ps3.setInt(1, id);
        ps3.setInt(1, idrac);
        rs=ps2.executeQuery();
        }
        String upit4="UPDATE Racun SET Stanje=Stanje+? WHERE IdRac=?";
        PreparedStatement st2=conn.prepareStatement(upit4);
        st2.setFloat(1,suma);
        st2.setInt(2, idrac);
        st2.executeUpdate();
        conn.commit();
        conn.setAutoCommit(true);
    }
    catch(Exception e)
    {
    System.out.println("Doslo je do greske");
    conn.rollback();
    }
        System.out.println("Uspesna realizacija");
    return suma;
    }
}
