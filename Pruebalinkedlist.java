package pruebalinkedlist;
/*
@author Diego Leyva Mendoza
@date 17-08-2026
*/

class Student {
    String name;
    int score;
    Student next;
    public Student(String name, int score){
        this.name = name;
        this.score = score;
        this.next = null;
    }
}

class LnkdLst{
    Student head;
    public LnkdLst(){ // Constructor
        this.head = null; // 
    }
    // Agrega un nuevo estudiante al final de la lista
    public void add(String name, int score){
        Student newStudent = new Student(name, score);
        if (head == null) {
            head = newStudent; //si head es nulo, agregamos nuevo estudiante (newStudent)
            return;
        } 
        Student current = head;
        while (current.next != null){
            current = current.next;
        }
        current.next = newStudent;
    }
}
public class Pruebalinkedlist {

    public static void main(String[] args) {
        LnkdLst lista = new LnkdLst();
        lista.add("Luis", 90);
        lista.add("Ada", 95);
        lista.add("Paul", 78);
    }

}