package br.com.linksport.network;

import java.io.Serializable;

public class Evento implements Serializable {

    private String timeCasa;
    private String timeFora;
    private String tempo;
    private String campeonato;
    private String placarCasa;
    private String placarFora;

    public Evento() {
    }

    public Evento(String timeCasa, String timeFora, String tempo, String campeonato, String placarCasa, String placarFora) {
        this.timeCasa = timeCasa;
        this.timeFora = timeFora;
        this.tempo = tempo;
        this.campeonato = campeonato;
        this.placarCasa = placarCasa;
        this.placarFora = placarFora;
    }

    public String getTimeCasa() {
        return timeCasa;
    }

    public void setTimeCasa(String time_casa) {
        this.timeCasa = time_casa;
    }

    public String getTimeFora() {
        return timeFora;
    }

    public void setTimeFora(String time_fora) {
        this.timeFora = time_fora;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(String campeonato) {
        this.campeonato = campeonato;
    }

    public String getPlacarCasa() {
        return placarCasa;
    }

    public void setPlacarCasa(String placar_casa) {
        this.placarCasa = placar_casa;
    }

    public String getPlacarFora() {
        return placarFora;
    }

    public void setPlacarFora(String placar_fora) {
        this.placarFora = placar_fora;
    }
}
