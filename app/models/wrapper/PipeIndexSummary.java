package models.wrapper;


public class PipeIndexSummary {
	
	public PipeIndexSummary (Float cdL, Float cqL, Float cdPipeLengthInspected, Float cdPipeLengthNotInspected, Float cqPipeLengthInspected, Float cqPipeLengthNotInspected, Float cdAndCqPipeLength) {
		
		this.pipeIndexSummaryUI = new PipeIndexSummaryUI();
		this.pipeIndexSummaryUI.conditionLimit = cdL;
		this.pipeIndexSummaryUI.consequenceLimit = cqL;
		this.pipeIndexSummaryUI.conditionIndexPipeLengthInspected = cdPipeLengthInspected / 1000F;
		this.pipeIndexSummaryUI.consequenceIndexPipeLengthInspected = cqPipeLengthInspected / 1000F;
		this.pipeIndexSummaryUI.conditionIndexPipeLengthNotInspected = cdPipeLengthNotInspected / 1000F;
		this.pipeIndexSummaryUI.consequenceIndexPipeLengthNotInspected = cqPipeLengthNotInspected / 1000F;
		this.pipeIndexSummaryUI.conditionIndexPipeLength = (cdPipeLengthNotInspected + cdPipeLengthInspected) / 1000F;
		this.pipeIndexSummaryUI.consequenceIndexPipeLength = (cqPipeLengthNotInspected + cqPipeLengthInspected) / 1000F;
		this.pipeIndexSummaryUI.conditionAndConsequencePipeLength = cdAndCqPipeLength / 1000F;
	}

	public PipeIndexSummaryUI pipeIndexSummaryUI; 

	
	public static class PipeIndexSummaryUI {
		
		public PipeIndexSummaryUI() {
			
		}
		public Float conditionLimit;
		public Float consequenceLimit;
		public Float conditionIndexPipeLengthInspected;
		public Float conditionIndexPipeLengthNotInspected;
		public Float conditionIndexPipeLength;
		public Float consequenceIndexPipeLength;
		public Float consequenceIndexPipeLengthInspected;
		public Float consequenceIndexPipeLengthNotInspected;
		public Float conditionAndConsequencePipeLength;
		
	}
	
}
