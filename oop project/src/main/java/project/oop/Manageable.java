/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package project.oop;

/**
 *
 * @author tawfik
 */
public interface Manageable<T> {
    void add(T obj);
    void update(T obj);
    void delete(T obj);   
}
