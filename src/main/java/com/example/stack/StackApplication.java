package com.example.stack;

import java.util.concurrent.atomic.*;

import com.example.Stack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@SpringBootApplication
@RestController
public class StackApplication {

	AtomicReferenceArray<Node<Object>> top = new AtomicReferenceArray<Node<Object>>(10000);
	private int size = 0;

	public static void main(String[] args) {
		SpringApplication.run(StackApplication.class, args);
	}
	/*
	** GET on /stack/size returns size of stack
	*/
	@RequestMapping(value = "/stack/size", method = RequestMethod.GET)
	public Stack stack() {
		return new Stack(size);
		
	}

	/*
	** PUT on /stack/{item} adds {item} to stack
	** size is incremented accordingly
	*/
	@RequestMapping(value = "/stack/{item}", method = RequestMethod.PUT)
	public @ResponseBody void push(@PathVariable Object item) {
		Node<Object> newHead = new Node<Object>(item);
		Node<Object> oldHead;
		size++;
		do {
			oldHead = top.get(size - 1);
			newHead.next = oldHead;

		} while (!top.compareAndSet(size - 1, oldHead, newHead));

	}

	/*
	** DELETE on /stack/pop pops top item off stack and returns it
	** size is decremented accordingly
	*/
	@RequestMapping(value = "/stack/pop", method = RequestMethod.DELETE)
	@ExceptionHandler(ResponseStatusException.class)
	public @ResponseBody Object pop() {
		if (size <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		}
		Node<Object> oldHead;
        Node<Object> newHead;
        do {
            oldHead = top.get(size - 1);
            if (oldHead == null)
                return null;
			newHead = oldHead.next;
			
		} while (!top.compareAndSet(size - 1,oldHead, newHead));
		size--;
        return oldHead.item;
	}

	/*
	** Node Class definition
	*/
	private static class Node<E> {
		public final Object item;
		public Node<Object> next;

		public Node(E item) {
			this.item = item;
		}


	}


}
