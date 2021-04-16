# Treball UF5: Hospital

L'aplicació et permet gestionar els pacients del hospital, les caràcteristiques del programa son les seguents:
- Permet afegir un nou pacient amb el camps `Nom, Cognoms, DNI, Edat, Sexe, Urgencia...`
    * El sexe del pacient es triarà mitjançant `RadioButtons`
    * La urgencia del pacient es seleccionarà mitjançant una `ComboBox` amb opcions predefinides
    * Els camps anteriors son obligatoris i es veurà el progrés de creació del pacient mitjançant una `ProgressBar`
    * La edat es calcularà automaticament, l'usuari ha de indicar la seva data de naixement mitjaçant un `DatePicker`
    * Es guardarà la `data d'entrada` i la `data de sortida` dels pacients automaticament
    * El pacient ha d'acceptar la llei de protecció de dades marcant un `CheckBox`
- Al crear un nou pacient s'ha de seleccionar una de les següents urgencies:
    * Cirugía General
    * Pediatría
    * Traumatología
    * Cardiología
    * Dermatología
    * Neurología
    * Altres
- Es podrà consultar la informació del pacient (_En una finestra a part_)
- Si vols donar de baixa a un pacient ho pots fer de dues maneres:
    * Si no selecciones el pacient es donará de baixa el primer pacient de la llista
    * Si selecciones un pacient es donarà de baixa el pacient seleccionat
        > Si la llista està buida apareix missatge d'error
- Al donar de baixa un pacient, es guardarà als Registres
    * Els registres permeten mostrar tots els pacients o ordenar per urgencia.
- El programa està disponible en Anglès, Català i Castellà