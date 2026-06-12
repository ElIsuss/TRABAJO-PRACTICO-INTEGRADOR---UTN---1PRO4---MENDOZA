package integrado.prog2.entities;

import java.time.LocalDateTime;

public class Base {
    private Long id;
    private Boolean eliminado;
    private LocalDateTime createdAd;
    private static Long auxId = 0L;

    public Base(){
        this.id= generarId();
        this.eliminado = false;
        this.createdAd = LocalDateTime.now();
    }

    private Long generarId(){
        return ++auxId;
    }

    //GETTERS//
    public Long getId() {
        return id;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public LocalDateTime getCreatedAd() {
        return createdAd;
    }
}