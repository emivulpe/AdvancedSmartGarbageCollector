package uk.ac.glasgow.etparser.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import uk.ac.glasgow.etparser.ObjectClass;

public class SmartHeapLargestSize extends SmartHeap {

	public SmartHeapLargestSize() {
		super();
		System.out.println("You created a new largestHeap");
	}

	protected void deallocate() {
		// create a list of objects ordered by the time of last access
		List<ObjectClass> timeOrderedObjects = getListOfObjectClassTimeSorted();
		while (!sizeNormal() && timeOrderedObjects.size() > 0) {
			// take the least recently used object and remove it from the list
			String currentObjectID = timeOrderedObjects.remove(0).getID();
			// kill that object in the ever seen so
			// it would be treated as a dead object from now on
			killObject(currentObjectID);
			deallocate(currentObjectID);
		}

	}

	public List<ObjectClass> getListOfObjectClassTimeSorted() {
		HashMap<String, ObjectClass> objects = getObjectStates();
		ArrayList<ObjectClass> listOfObjects = new ArrayList<ObjectClass>();
		for (ObjectClass obj : objects.values()) {
			listOfObjects.add(obj);

		}

		Collections.sort(listOfObjects, new Comparator<ObjectClass>() {
			public int compare(ObjectClass o2, ObjectClass o1) {
				return Integer.compare(o1.getSize(), o2.getSize());

			}
		});

		return listOfObjects;
	}
}