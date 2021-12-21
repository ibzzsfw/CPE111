import java.util.Scanner;

public class Assignment1 {

    static int getInt(String message, int minimum, int maximum) {// รับค่าจำนวนเต็มหนึ่งตัว

        int __int__ = 0;// กำหนดค่าเริ่มต้นให้เป็น 0
        boolean success = false;// ความสำเร็จในการรับเป็น false (ยังรับไม่สำเร็จ)

        Scanner scanner = new Scanner(System.in);// สร้างออบเจ็ค scanner เพื่อใช้ในการรับค่าจากคีย์บอร์ด
        while (!success) {//ในขณะที่ยัง true
            System.out.print(message);//แสดงผลข้อความเริ่มต้นเพื่อแจ้งให้ผู้ใช้ป้อนค่า
            try {//ลองดำเนินการ
                __int__ = scanner.nextInt();//อ่านจำนวนเต็มเก็บใน __int__
                if (__int__ >= minimum && __int__ <= maximum)//ถ้า __int__ อยู่ในช่วงที่กำหนด
                    success = true;//ความสำเร็จในการรับเป็น true (รับสำเร็จ)
                else//มิฉะนั้น
                    System.out.println("Input error, please enter between " + minimum + " - " + maximum);//แจ้งให้ป้อนค่าในช่วงที่กำหนด
            } catch (Exception e) {//แก้ไขปัญหา
                System.out.println("Input error, please enter between " + minimum + " - " + maximum);//แจ้งให้ป้อนค่าในช่วงที่กำหนด
                scanner.nextLine();//อ่านใหม่โดยไม่เก็บตัวค่าเดิม
            }
        }
        return __int__;//return ค่า __int__
    }

    public static long factorial(int n, int count) {//คำนวณค่า n! และอธิบายแต่ละขั้นของการเรียกซ้ำ

        long stackAnswer = 0;//ให้ stackAnswer เริ่มต้นเป็น 0

        if (n == 0) {//ถ้า n = 0
            System.out.println("0! is base case return answer of 0! = 1");//แสดงผล 0! is base case return answer of 0! = 1 แล้วขึ้นบรรทัดใหม่
            return 1;
        } else {//มิฉะนั้น (n = 1,2,3,...,maximum __int__)
            System.out.println("---------- Loop "+ count++ + "----------");
            System.out.println(n + "! is recursive case. Answer = " + n + " * recursion of " + (n - 1) + "!");//แสดงผล n! is recursive case. Answer = n*recursion of (n - 1)! แล้วขึ้นบรรทัดใหม่
            System.out.println("\tRecursion to calculate " + (n - 1) + "!");//แสดงผล    Recursion to calculate (n - 1)! แล้วขึ้นบรรทัดใหม่
            stackAnswer = n * factorial(n - 1, count);//ให้ stackAnswer เป็น  n(n-1)! 
            //เกิดการเรียกซ้ำ
            System.out.println("Calculate " + (n - 1) + "! complete.");//แสดงผล Calculate (n - 1)! complete. แล้วขึ้นบรรทัดใหม่
            System.out.println("\tReturn answer from " + (n - 1) + "! = " + (stackAnswer / n) + " to calculate " + n
                    + "! = [" + n + " * " + (n - 1) + "!] = " + n + " * " + (stackAnswer / n) + " = " + stackAnswer);
                    //แสดงผล    Return answer from (n - 1)! = (stackAnswer / n) to calculate n! = [n*(n - 1)!] = n*(stackAnswer / n) = stackAnswer แล้วขึ้นบรรทัดใหม่
            return stackAnswer;//ซึ่งเป็นค่า n(เริ่มต้น)!
        }
    }

    public static void main(String[] args) {

        int n = 0;//กำหนดค่า n ที่ใช้ในแฟคทอเรียล เริ่มต้นเป็น 0
        int count = 0;
        long answer = 0;//กำหนดค่า n! เริ่มต้นเป็น 0
        char continueProgram = '\0';//กำหนดค่า ตัวเลือกการใช้งานโปรแกรมต่อ เริ่มต้นเป็นสตริงว่าง

        Scanner scanner = new Scanner(System.in);// สร้างออบเจ็ค scanner เพื่อใช้ในการรับค่าจากคีย์บอร์ด
        System.out.println("Suppakorn Rakna's Rcursion Program.");//แสดงผล Suppakorn Rakna's Rcursion Program. แล้วขึ้นบรรทัดใหม่
        System.out.println("Program calculate n! by recursion (n<=15)");//แสดงผล Program calculate n! by recursion (n<=15) แล้วขึ้นบรรทัดใหม่

        do {//ทำ
            count = 1;
            n = getInt("Enter n = ", 0, 15);//รับค่าจำนวนเต็มตั้งแต่ 0 ถึง 15 มาเก็บไว้ที่ n
            answer = factorial(n, count);//คำนวณ n! มาเก็บไว้ที่ answer
            //sensitive case
            if (n == 0)//ถ้า n = 0
                System.out.println("Calculate 0! complete.");//แสดงผล Calculate 0! complete. แล้วขึ้นบรรทัดใหม่
            //จบ sensitive case    
            System.out.println("Complete calculation of " + n + "! , answer = " + answer);//Complete calculation n! , answer<string> = answer<long> แล้วขึ้นบรรทัดใหม่

            System.out.printf("press [y] to continue, other to exit. ");//แสดงผล press [y] to continue, other to exit. เพื่อถามผู้ใช้
            if (scanner.hasNext()) {//ตรวจสอบการอ่าน
                continueProgram = scanner.next().charAt(0);//อ่านสตริงและตรวจสอบตัวอักษรที่ตำแหน่ง 0 เก็บใน continueProgram
            }
        } while (continueProgram == 'y' || continueProgram == 'Y');//ขณะที่ continueProgram เป็น 'Y' หรือ 'y'

        System.out.println("End Program.");//แสดงผล End Program. แล้วขึ้นบรรทัดใหม่
        System.out.println("Program writen by 63070501061 Suppakorn Rakna");//แสดงผล Program writen by 63070501061 Suppakorn Rakna แล้วขึ้นบรรทัดใหม่
    }
}