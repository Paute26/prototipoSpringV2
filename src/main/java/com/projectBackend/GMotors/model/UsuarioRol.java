package com.projectBackend.GMotors.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@IdClass(UsuarioRol.UsuarioRolId.class)
@Entity
@Table(name = "usuario_rol")
public class UsuarioRol {

    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Id
    @Column(name = "id_rol")
    private Integer idRol;

    public UsuarioRol(){}

    public UsuarioRol(Integer idUsuario, Integer idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    // -------------------------------
    // Clase interna para la PK compuesta
    // -------------------------------
    public static class UsuarioRolId implements Serializable {

        private Integer idUsuario;
        private Integer idRol;

        public UsuarioRolId() {}

        public UsuarioRolId(Integer idUsuario, Integer idRol) {
            this.idUsuario = idUsuario;
            this.idRol = idRol;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UsuarioRolId)) return false;
            UsuarioRolId that = (UsuarioRolId) o;
            return Objects.equals(idUsuario, that.idUsuario) &&
                   Objects.equals(idRol, that.idRol);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idUsuario, idRol);
        }
    }
}
