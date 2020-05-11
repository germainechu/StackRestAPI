package com.example.stack;

import java.util.concurrent.atomic.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StackApplication {

	AtomicReferenceArray<Node<Object>> top = new AtomicReferenceArray<Node<Object>>(10000);

	public static void main(String[] args) {
		SpringApplication.run(StackApplication.class, args);
	}

	@RequestMapping(value = "/stack", method = RequestMethod.GET)
	public int stack() {
		return top.length();
	}

	@RequestMapping(value = "/stack/push/{item}", method = RequestMethod.PUT)
	public @ResponseBody void push(@RequestBody Object item) {
		Node<Object> newHead = new Node<Object>(item);
		Node<Object> oldHead;
		do {
			oldHead = top.get(top.length() - 1);
			newHead.next = oldHead;

		} while (!top.compareAndSet(0, oldHead, newHead));

	}

	@RequestMapping(value = "/stack/pop", method = RequestMethod.DELETE)
	public @ResponseBody Object pop() {
		if (top.length() == 0) {
			try {
				throw new Exception("Error Code: 100");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Node<Object> oldHead;
        Node<Object> newHead;
        do {
            oldHead = top.get(top.length()-1);
            if (oldHead == null)
                return null;
            newHead = oldHead.next;
        } while (!top.compareAndSet(0,oldHead, newHead));
        return oldHead.item;
	}

	private static class Node<E> {
		public final Object item;
		public Node<Object> next;

		public Node(E item) {
			this.item = item;
		}


	}


}
