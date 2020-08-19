package edu.upenn.cit594.datamanagement;

import java.util.List;
/**
 * 
 * @author anmorr and ryanng
 *
 * @param <T>
 * 
 * This is an interface for all file readers
 * used in this program.
 * 
 */
public interface Reader<T> {
	
	public List<T> read();

}
