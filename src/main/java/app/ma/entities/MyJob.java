package app.ma.entities;

import java.util.Random;

import app.ma.enums.Veredict;
import app.ma.repositories.SubmitRepository;

public class MyJob implements Runnable {
	private SubmitRepository submitRepository;

    private final Submit submit;
    Random z = new Random(System.currentTimeMillis());

    public MyJob(Submit submit, SubmitRepository submitRepository) {
        this.submit = submit;
        this.submitRepository = submitRepository;
    }

    @Override
    public void run() {
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {e.printStackTrace();}
    	
    	System.out.println(submit.getProblemContestUser().problemContest == null);
    	Veredict veredict = Veredict.values()[z.nextInt(Veredict.values().length-1) + 1];
    	if(!veredict.equals(Veredict.Compilation_Error)) {
    		Problem problem = this.submit.getProblemContestUser().problemContest.problem;
    		if(veredict.equals(Veredict.Time_Limit))
    			this.submit.setTimeConsumed(problem.getTimeLimit());
    		else
    			this.submit.setTimeConsumed((long)z.nextInt(problem.getTimeLimit().intValue()));
    		if(veredict.equals(Veredict.Memory_Limit))
    			this.submit.setMemoryConsumed(problem.getMemoryLimit());
    		else
    			this.submit.setMemoryConsumed((long)z.nextInt(problem.getMemoryLimit().intValue()));
    	}
    	this.submit.setVeredict(veredict);
    	this.submitRepository.save(submit);
    }
}