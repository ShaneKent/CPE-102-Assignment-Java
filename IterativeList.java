import java.util.NoSuchElementException;

public class IterativeList<T>{

   private static class Node<R> {
      public R value;
      public Node<R> next;

      public Node(R value, Node<R> next){
         this.value = value;
         this.next = next;
      }
   }

   private Node<T> head;

   public IterativeList(){
      head = null;
   }

   public void addToEnd(T value){
      if (head == null){
         head = new Node<T>(value, null);
      }
      else{
         Node<T> cur = head;
         while (cur.next != null){
            cur = cur.next;
         }
         cur.next = new Node<T>(value, null);
      }
   }

   public void add(int index, T value){
      if (index < 0 || index > size()){// || head == null){
         throw new IndexOutOfBoundsException();
      }

      if (index == size()){
         addToEnd(value);
      }
      else{
         if (index == 0){
            Node<T> newNode = new Node<T>(value, head);
            head = newNode;
         }
         else{
            Node<T> prev = null;
            Node<T> cur = head;
            int i = 0;
            while (i < index){
               prev = cur;
               cur = cur.next;
               i++;
            }
            Node<T> newNode = new Node<T>(value, cur);
            prev.next = newNode;
         }
      }
   }

   public void remove(int index){
      if (index < 0 || index > size() || head == null){
         throw new IndexOutOfBoundsException();
      }
      
      Node<T> cur = head;
      Node<T> prev = null;

      if(index == 0){
         head = cur.next;
      }
      else{
         int i = 0;
         while (i < index){
            prev = cur;
            cur = cur.next;
            i++;
         }
         prev.next = cur.next;
      }
   }

   public T get(int index){
      if (index < 0 || index > size() || head == null){
         throw new IndexOutOfBoundsException();
      }
      
      Node<T> cur = head;
      if (index == 0){
         return cur.value;
      }

      int i = 0;
      while (i < index && cur != null){
         cur = cur.next;
         i++;
      }
      return cur.value;
   }

   public int indexOf(T value){
      Node<T> cur = head;
      int i = 0;
      while (cur != null){
         if (cur.value.equals(value)){
            return i;
         }
         cur = cur.next;
         i++;
      }
      return -1;
      //throw new NoSuchElementException();
   }
   
   public int size(){
      Node<T> cur = head;
      
      int i = 0;
      while (cur != null){
         cur = cur.next;
         i++;
      }
      
      return i;
   }
   /*
   public <R> SimpleList<R> map(java.util.function.Function<T, R> function){
      SimpleList<R> newList = new IterativeList<R>();
      Node<T> cur = head;

      while (cur != null){
         newList.addToEnd(function.apply(cur.value));
         cur = cur.next;
      }
   
      return newList;
   }
   */
}
