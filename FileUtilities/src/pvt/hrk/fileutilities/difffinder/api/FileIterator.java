package pvt.hrk.fileutilities.difffinder.api;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

public class FileIterator implements Iterator<File> {

	Queue<File> fileQueue;

	public FileIterator(File initialFile) {
		/**
		 * This needs to be a priority queue as we want the elements sorted.
		 * Since we are comparing the files between two iterators, the "next" method must be 
		 * returning values in a certain order.
		 */
		fileQueue = new PriorityQueue<>((file1, file2) -> file1.getName().compareTo(file2.getName()));
		fileQueue.add(initialFile);
	}

	@Override
	public boolean hasNext() {
		return fileQueue != null && !fileQueue.isEmpty();
	}

	@Override
	public File next() {
		if(hasNext())
		return fileQueue.poll();
		else
			throw new NoSuchElementException("No next element available for this iterator");
	}

	public void addToIterator(File f) {
		fileQueue.add(f);
	}
}
