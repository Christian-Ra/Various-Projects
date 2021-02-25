.data
	msg1: .asciiz "MIPS Calculator"
	dashes: .asciiz "\n==========================================" #Just for UI enhancment
	options: .asciiz "\n1.Addition\n2.Substraction\n3.Multiplication\n4.Division\n5.Modulo\n6.Square\n7.Summation\n8.Factorial\n9.Conversions\n\n\n\n10.Exit" #list of operations - conversions takes user to 2nd menu
	msg2: .asciiz "\n1.Binary to Decimal\n2.Decimal to Binary\n3.Decimal to Hexa\n4.Hexa to Decimal\n5.Main Menu\n10.Exit" #Menu for different conversions
	bye: .asciiz "Goodbye!" #exit message
	null: .asciiz ""
	space: .asciiz " "
	newLine: .asciiz "\n" #Used after error messages
	newLines: .asciiz "\n\n\n"
	prompt1: .asciiz "Please enter a number:"
	prompt2: .asciiz "Please enter another number:"
    sumPrompt: .asciiz "Please enter a value greater then your first input"
	prompt3: .asciiz "Would you like to do anymore calculations? 0 for no, 1 for yes:" 
	prompt4: .asciiz "Press enter to continue"
	error1: .asciiz "Please enter a valid choice"
	error2: .asciiz "Invalid number"
	divError: .asciiz "Cannot divide by 0"
	binMsg: .asciiz "Enter a binary number:"
	decMsg: .asciiz "Enter a decimal number:"
	hexMsg: .asciiz "Enter a hexadecimal number:"
    sumErrorMsg: .asciiz "Invalid input, please input 2 numbers where the 1st > 2nd"
    factorialErrorMsg: .asciiz "Invalid input, please enter a integer > 0"
	empty: .space 32
	result: .space 32
	answer: .space 8
	input: .space 8
	inputArray: .space 32
.text
main:
	#load interface for calculator
	Interface:
	li $v0, 4
	la $a0, msg1
	syscall
	li $v0, 4
	la $a0, dashes
	syscall
	li $v0, 4
	la $a0, options
	syscall
	li $v0, 4
	la $a0, newLine
	syscall
	li $v0, 5	#store input from user
	syscall
	#check what input of user for selection
	move $t0, $v0
	beq $t0, 1, Addition	#go to addition function
	beq $t0, 2, Subtraction	#go to subtraction function
	beq $t0, 3, Multiplication	#go to multiplication function
	beq $t0, 4, Division	#go to division function
    beq $t0, 5, Modulo #go to modulo funtion
    beq $t0, 6, Square #go to square function
    beq $t0, 7, Summation #go to summation function
    beq $t0, 8, Factorial #go to factorial function
    beq $t0, 9, Interface2 #go to 2nd interface for conversions
	beq $t0, 10, Exit	#Exit if value is 10
	j Error

#######################################################
#interface for conversions
	Interface2:
	li $v0, 4 #General UI/ Visiual stuff
	la $a0, newLines
	syscall
	li $v0, 4
	la $a0, msg2
	syscall
	li $v0, 4
	la $a0, newLine
	syscall
	li $v0, 5
	syscall
	move $t0, $v0
	li $v0, 4
	la $a0, newLines
	syscall
	beq $t0, 1, BinToDec	#done
	beq $t0, 2, DecToBin	#done
	beq $t0, 3, DecToHex	#Works? Outputs the correct value but reversed, not sure how to fix.
	beq $t0, 4, HexToDec	#done
    # beq $t0, 5, HextoBin (Couldn't finish in time)
    #beq $t0, 6, BintoHex (Couldn't finish in time)
	beq $t0, 5, Interface
	beq $t0, 10, Exit
	j Error2
	
####################################################################
	BinToDec:	#1
	li $v0, 4
	la $a0, binMsg
	syscall
	
	li $v0, 8
	la $a0, empty
	li $a1, 16	#load 16 as max len to read into a1
	syscall
	
	li $v0, 4
	la $a0, empty
	syscall
	
	li $t4, 0	#initalize sum to 0
	
	startConvert:
	la $t1, empty
	li $t9, 16
	
	firstByte:
	lb $a0, ($t1)	#load first byte
	blt $a0, 48, printSum	
	addi $t1, $t1, 1	#increment offset
	addi $a0, $a0, -48	#sub 48 to convert int value
	addi $t9, $t9, -1
	beq $a0, 0, isZero	#if byte equals zero then do is Zero
	beq $a0, 1, isOne
	j convert
	
	isZero:
	j firstByte	#decrement counter
	
	isOne:		#2^counter
	li $t8, 1	#load 1
	sllv $t5, $t8, $t9	#shift left by counter = 1*2^counter
	add $t4, $t4, $t5	#add sum to prev sum
	li $v0, 1	#print int
	move $a0, $t4
	j firstByte
	
	convert:
	printSum:
	srlv $t4, $t4, $t9
	li $v0, 1
	move $a0, $t4	#load sum
	syscall		#print int
	j MoreCalculations

	
####################################################################
	DecToBin:	#3
	li $v0, 4
	la $a0, decMsg
	syscall
	
	li $v0, 5
	syscall
	move $t0, $v0
	
	#create mask/print out the second string and prepare to print out binary
	mask: 
  	addi $t1, $zero, 1
  	sll $t1, $t1, 31
  	addi $t2, $zero, 32

 	# compares mask to integer, starting at the most sig place
 	# if the mask is zero, print out zero
 	loop3: 
  	and $t3,$t0, $t1
  	beq $t3, $zero, print3
  	add $t3, $zero, $zero
  	addi $t3, $zero, 1
  	j print3

 	print3: 
  	li $v0, 1
 	move $a0, $t3
  	syscall
  	srl $t1, $t1, 1
	addi $t2, $t2, -1

	bne $t2, $zero, loop3
  	beq $t2, $zero, MoreCalculations

	
#####################################################################	
	DecToHex:	#4
	li $v0, 4
	la $a0, decMsg
	syscall
	
	li $v0, 5       #allow for input of integer
        syscall
	
	la $a1, answer      # load the address of answer into $a1
        addi $t0, $zero, 48 #set $t0 to 48
        sb $t0, 0($a1)          #store 48 at location 0 in $a1 
        addi $t0, $0, 120       #set $t0 equal to 120
        sb $t0, 1($a1)          #store 120 at location 1 in $a1
        addi $a1, $a1, 1

        add $a0, $v0, $zero #add the input to the $a0 register
        jal print_hex1

	li $v0, 4
        la $a0, answer
        syscall         #print out hex answer to console

        j MoreCalculations


	print_hex1:
        andi $t0, $a0, 0xf
        addi $a1, $a1, 1

        ble $t0, 10, lessThanTen1        
        addi $t0, $t0, 39   
        lessThanTen1:
        addi $t0, $t0, 48
        sb $t0, 0($a1)
        srl $a0, $a0, 4
        bne $a0, $zero, print_hex1
    	done1:
        jr $ra
####################################################################
	HexToDec:	#5
	li $v0, 4
	la $a0, hexMsg
	syscall
	li $v0, 8
	la $a0, inputArray
	li $a1, 32
	syscall
   	 # start counter
    	la   $t2, inputArray       # load inputNumber address to t2
    	li   $t8, 1                      # start our counter
    	li   $a0, 0                      # output number
   	j    HexToDecLoop

	HexToDecLoop:
    	lb   $t7, 0($t2)
   	ble  $t7, '9', inputSub48       # if t7 less than or equal to char '9' 
    	addi $t7, $t7, -55              # convert from string to int
    	j    inputNormalized
	
	inputNormalized:
   	blt  $t7, $zero, Finish  	# print int if t7 < 0
    	li   $t6, 16                    # load 16 to t6
    	mul  $a0, $a0, $t6              # t8 = t8 * t6
    	add  $a0, $a0, $t7              # add t7 to a0
    	addi $t2, $t2, 1                # increment array position
    	j HexToDecLoop

	inputSub48:
   	addi $t7, $t7, -48              # convert from string (ABCDEF) to int
  	j inputNormalized
	
	Finish:
	li $v0, 1
	move $a0, $a0
	syscall
	j MoreCalculations
	
################################################################################

#Standard Calculator Calculations

	Addition:	#########1
	#prompt user to input two numbers
	li $v0, 4
	la $a0, prompt1
	syscall
	li $v0, 5
	syscall
	move $t0, $v0
	li $v0, 4
	la $a0, prompt2
	syscall
	li $v0, 5
	syscall
	move $t1, $v0
	add $t2, $t0, $t1
    j printResult
	
	Subtraction:	# #2
	#prompt user to input two numbers
	li $v0, 4
	la $a0, prompt1
	syscall
	li $v0, 5
	syscall
	move $t0, $v0	#copy value from $v0, $t0
	li $v0, 4
	la $a0, prompt2
	syscall
	li $v0, 5
	syscall
	move $t1, $v0	#copies values from $v0 to $ t1
	sub $t2, $t0, $t1
	j printResult
	
	Multiplication:	# #3
	#prompt user to input two numbers
	li $v0, 4
	la $a0, prompt1
	syscall
	li $v0, 5
	syscall
	move $t0, $v0		#place value in $t0
	li $v0, 4
	la $a0, prompt2
	syscall
	li $v0, 5
	syscall
	move $t1, $v0		#place value in $t1
	mul $t2,$t0, $t1	#multiply integers
	j printResult
	
	Division:	# #4
	#prompt user to input two numbers
	li $v0, 4
	la $a0, prompt1
	syscall
	li $v0, 5
	syscall
	move $t0, $v0
	li $v0, 4
	la $a0, prompt2
	syscall
	li $v0, 5
	syscall
	move $t1, $v0
	beqz $t1, DivError
	div $t2, $t0, $t1
	j printResult

    Modulo: # #5
    #Prompt user to input two numbers
    li $v0, 4
    la $a0, prompt1 
    syscall
    li $v0, 5
    syscall
    move $t0, $v0
    li $v0, 4
    la $a0, prompt2
    syscall
    li $v0, 5
    syscall
    move $t1, $v0
    div $t2, $t0, $t1 # In MIPS, remainder from register in division is stored in HI register
    li $v0, 1 
    mfhi $a0 #Moving value from HI Register to output
    syscall
    j MoreCalculations

    Square: # #6
    #Prompt user to input one number to be squared
    li $v0, 4
    la $a0, prompt1
    syscall
    li $v0, 5
    syscall
    move $t0, $v0 
    mul $t2, $t0, $t0		# Multiply $t0 by itself to get the sqaure 
    j printResult

    Summation: # #7  Using Summation formula (m+n)(m-n + 1) / 2 where m > n
    #prompt user for two numbers, where the first must be greater then the 2nd
    li $v0, 4
    la $a0, prompt1
    syscall
    li $v0, 5
    syscall
    move $t0, $v0
    li $v0, 4
    la $a0, sumPrompt
    syscall 
    li $v0, 5
    syscall
    move $t1, $v0
    slt $t6, $t1, $t0 #Checks input to verify that m > n, if not, jump to error message
    beq $t6, 1, sumError
    add $t3, $t1, $t0 #(m+n)
    sub $t4, $t1, $t0
    add $t4, $t4, 1 #(m-n + 1)
    mul $t5, $t3, $t4 # Multiply registers t4 and t5: (m+n) * ((m-n) + 1 )
    div $t2, $t5, 2, # Divide total by 2 to return answer
    j printResult

    Factorial: #Using a loop, take input and multiply by a decrementing value (n - 1) 
    #Ask user for input
    li $v0, 4
    la $a0, prompt1
    syscall
    li $v0, 5
    syscall 
    move $t0, $v0
    beq $t0, 0, zeroFactorial #if input is zero, jump to zeroFactorial branch
    beq $t0, 1, zeroFactorial #1! and 0! are the same
    slti $t2, $t0, 0
    beq $t2, 1, factorialError #If input is not > zero, check input to ensure value is greater than 0
    addi $t1, 1 #Default value to insure factorial loop is inclusive
    FactorialLoop: 
    mul $t1, $t1, $t0 # n * (n-1)
    addi $t0, $t0, -1 #decrement
    bgtz $t0, FactorialLoop # if $t0 > 0, continue through loop
    j factorialResult
    

    factorialResult: #printMethod function has problems with factorial(?), so a seperate output function was made
    li $v0, 1
    move $a0, $t1
    syscall
    j MoreCalculations

    zeroFactorial: # zero factiorial is equal to 1
    addi $t2, 1
    li $v0, 1
    move $a0, $t2
    syscall
    j MoreCalculations 

	Error:	######
	li $v0, 4
	la $a0, error1
	syscall
	li $v0, 4
	la $a0, newLines
	syscall
	j Interface
	
	Error1:	######
	li $v0, 4
	la $a0, error1
	syscall
	li $v0, 4
	la $a0, newLines
	syscall
	j MoreCalculations
	
	Error2:	######
	li $v0, 4
	la $a0, error1
	syscall
	li $v0, 4
	la $a0, newLines
	syscall
	j Interface2
	
	DivError:	#######
	li $v0, 4
	la $a0, divError
	syscall
	li $v0, 4
	la $a0, newLines
	syscall
	j Division

    sumError: ## Displays if input does not allow for summation (m < n) 
    li $v0, 4
    la $a0, sumErrorMsg
    syscall
    li $v0, 4
    la $a0, newLines
    syscall
    j Summation

    factorialError:
    li $v0, 4
    la $a0, factorialErrorMsg
    syscall
    li $v0, 4
    la $a0, newLines
    syscall
    j Factorial


    printResult: #standard output method for most operations
    li $v0, 1
	move $a0, $t2
	syscall
	j MoreCalculations
	
	MoreCalculations:	######
	li $v0, 4
	la $a0, newLines
	syscall
	li $v0, 4
	la $a0, prompt3
	syscall
	li $v0, 5	#get value from user
	syscall
	move $t0, $v0
	beq $t0, $zero, Exit
	beq $t0, 1, Interface
	j Error1

	Exit:
	li $v0, 4
	la $a0, bye
	syscall
	li $v0, 10
	syscall