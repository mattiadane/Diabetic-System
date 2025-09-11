package com.dashapp.diabeticsystem.models;


public class Diabetologo extends Persona  {

    /**
     * Id del diabetologo che ha effettuato il login
     */
    private int id_diabetologo ;


    public Diabetologo(String nome, String cognome, String email,String codice_fiscale,String sesso){
        super(nome,cognome,email,codice_fiscale,sesso);
    }
    public Diabetologo(int id_diabetologo, String  nome, String cognome, String email,String codice_fiscale,String sesso){
        this(nome,cognome,email,codice_fiscale,sesso);
        this.id_diabetologo = id_diabetologo;
    }







/*
    public List<Paziente> notifyPatient(){
        List<Paziente> pazienti = new  ArrayList<>();
        for(Paziente paziente : this.pazienti){
            int count = 0;
            for(Terapia t : paziente.getAllTerapie()){
                if(t.getData_inizio().isBefore(LocalDate.now().minusDays(2)) || t.getData_inizio().equals(LocalDate.now().minusDays(2))){
                    count++;
                }
            }
            if(count > 0){
                boolean check = (paziente.numberDailyTakingMedicine(LocalDate.now()) + paziente.numberDailyTakingMedicine(LocalDate.now().minusDays(1)) + paziente.numberDailyTakingMedicine(LocalDate.now().minusDays(2))) == 0;

                if(check)
                    pazienti.add(paziente);
            }
        }

        return pazienti;
    }*/

/*
    public Map<Paziente,List<Insulina>> notifyBloodSugar(){
        Map<Paziente,List<Insulina>> pazienti = new HashMap<>();
        for(Paziente paziente : this.pazienti){
            ObservableList<Insulina> dailyBloodSugar = paziente.getInsulinaByDate(LocalDate.now().atStartOfDay(),LocalDate.now().atTime(LocalTime.MAX));
            if(!dailyBloodSugar.isEmpty()){
                List<Insulina> bloodSugar = new ArrayList<>();
                for(Insulina glicemia : dailyBloodSugar){
                    if(glicemia.getGravita() != GRAVITA.NORMALE){
                        bloodSugar.add(glicemia);
                    }
                }
                pazienti.put(paziente,bloodSugar);

            }

        }

        return pazienti;
    }*/




    /**
     * Funzione per settare l'id del paziente
     * @param id_diabetologo di da assegnare al paziente
     */
    public void setId_diabetologo(int id_diabetologo) {
        this.id_diabetologo = id_diabetologo;
    }

    public int getId_diabetologo() {
        return id_diabetologo;
    }


}
