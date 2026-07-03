package kasir.Model;

public class user {
    private String idUser;
    private String namaKasir;
    private String username;
    private String password;
    private String status;

    public user(){}
    public user(String idUser, String namaKasir, String username, String password, String status){
        this.idUser = idUser;
        this.namaKasir = namaKasir;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    // Getter dan Setter
    public String getIdUsr(){
        return idUser;
    }
    public void setIdUser(String idUser){
        this.idUser = idUser;
    }

    public String getNamaKasir(){
        return namaKasir;
    }
    public void setNamaKasir(String namaKasir){
        this.namaKasir = namaKasir;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
