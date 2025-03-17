import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Block {
    private String previousHash;
    private String vote;
    private String hash;
    
    public Block(String vote, String previousHash) {
        this.vote = vote;
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }
    
    public String calculateHash() {
        try {
            String data = previousHash + vote;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getHash() {
        return hash;
    }
    
    public String getPreviousHash() {
        return previousHash;
    }
    
    public String getVote() {
        return vote;
    }
}

class Blockchain {
    private List<Block> chain;
    
    public Blockchain() {
        chain = new ArrayList<>();
        // Genesis block
        chain.add(new Block("Genesis Block", "0"));
    }
    
    public void addVote(String vote) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(vote, previousBlock.getHash());
        chain.add(newBlock);
        System.out.println("Vote recorded: " + vote);
    }
    
    public void displayBlockchain() {
        System.out.println("\nBlockchain:");
        for (Block block : chain) {
            System.out.println("Vote: " + block.getVote());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("------------------------------");
        }
    }
}

public class BlockchainVotingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Blockchain blockchain = new Blockchain();
        
        while (true) {
            System.out.println("\nBlockchain Voting System");
            System.out.println("1. Vote for Shivanand");
            System.out.println("2. Vote for Tarun");
            System.out.println("3. Vote for Yakshit");
            System.out.println("4. View Blockchain");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    blockchain.addVote("Shivanand");
                    break;
                case 2:
                    blockchain.addVote("Tarun");
                    break;
                case 3:
                    blockchain.addVote("Yakshit");
                    break;
                case 4:
                    blockchain.displayBlockchain();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
