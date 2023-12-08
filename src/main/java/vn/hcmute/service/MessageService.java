package vn.hcmute.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.hcmute.Responsitory.MessageRepository;
import vn.hcmute.entities.MessagesEntity;
import vn.hcmute.entities.UserInfoEntity;
import vn.hcmute.model.MessageModel;
import vn.hcmute.util.FireBaseUtil;

@Service
public class MessageService {
	private static final DatabaseReference databaseReference;
	private static final FirebaseDatabase firebaseDatabase;

	@Autowired
	private MessageRepository messageRepository;

	static {
		databaseReference = FirebaseDatabase.getInstance().getReference();
		firebaseDatabase = FirebaseDatabase.getInstance();
	}

	@Autowired
	public MessageService(FirebaseApp firebaseApp, MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public List<UserInfoEntity> listInfoReceiverByIdAccount(List<Long> conversationId) {
		List<UserInfoEntity> list = new ArrayList<>();
		for (Long recvId : conversationId) {
			list.add(messageRepository.receiver(recvId));
		}

		return list;
	}

	public void sendMessage(MessageModel message, long user1, long user2) {
		String conversationId = generateConversationId(user1, user2);
		createConversation(user1, user2);

		DatabaseReference newMessageRef = firebaseDatabase.getReference("RealTimeChat").child(conversationId)
				.child("messages").push();
		newMessageRef.setValue(message, new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
				if (databaseError == null) {
					System.out.println("Message sent successfully");
				} else {
					System.err.println("Error sending message: " + databaseError.getMessage());
				}
			}
		});
	}

	public void saveMessageToDatabase(MessagesEntity message) {
		messageRepository.save(message);
	}

	public List<MessagesEntity> getAllMessagesFromDatabase() {
		return messageRepository.findAll();
	}

	public List<MessagesEntity> getAllMessagesFromFirebase(long user1, long user2) {
		String conversationId = generateConversationId(user1, user2);
		createConversation(user1, user2);

		// Tạo một tham chiếu đến nút "messages"
		DatabaseReference messagesRef = databaseReference.child("messages").child(conversationId).child("messages");

		List<MessagesEntity> messages = new ArrayList<>();

		// Lắng nghe sự kiện thay đổi dữ liệu
		messagesRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
					// Lấy dữ liệu của mỗi message
					MessagesEntity message = messageSnapshot.getValue(MessagesEntity.class);

					// Thêm message vào danh sách
					messages.add(message);
				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				// Handle error
			}
		});
		return messages;
	}

	/* Create conversation */
	public void createConversation(long userId1, long userId2) {
		String conversationName = generateConversationId(userId1, userId2);
		DatabaseReference newConversationRef = firebaseDatabase.getReference("RealTimeChat").child(conversationName);

		FireBaseUtil.isNodeExists(newConversationRef, new FireBaseUtil.NodeExistenceCallback() {
			@Override
			public void onNodeExists(boolean exists) {
				if (!exists) {
					newConversationRef.setValue(true, new DatabaseReference.CompletionListener() {
						@Override
						public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
							if (databaseError == null) {
								System.out.println("Conversation created successfully");
							} else {
								System.out.println("Error creating conversation: " + databaseError.getMessage());
							}
						}
					});

					newConversationRef.child("members").child(String.valueOf(userId1)).setValue(true,
							new DatabaseReference.CompletionListener() {
								@Override
								public void onComplete(DatabaseError databaseError,
										DatabaseReference databaseReference) {
									if (databaseError != null) {
										System.out.println("Error setting member node: " + databaseError.getMessage());
									}
								}
							});
					newConversationRef.child("members").child(String.valueOf(userId2)).setValue(true,
							new DatabaseReference.CompletionListener() {
								@Override
								public void onComplete(DatabaseError databaseError,
										DatabaseReference databaseReference) {
									if (databaseError != null) {
										System.out.println("Error setting member node: " + databaseError.getMessage());
									}
								}
							});
					newConversationRef.child("messages").setValue(true, new DatabaseReference.CompletionListener() {
						@Override
						public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
							if (databaseError != null) {
								System.out.println("Error setting messages node: " + databaseError.getMessage());
							}
						}
					});
				} else {
					System.out.println("Conversation already exists");
				}
			}
		});
	}

	/* Create Conversation id */
	public String generateConversationId(long userId1, long userId2) {
		if (userId1 < userId2) {
			List<String> userIds = Arrays.asList(String.valueOf(userId1), String.valueOf(userId2));
			return String.join("_", userIds);
		} else {
			List<String> userIds = Arrays.asList(String.valueOf(userId2), String.valueOf(userId1));
			return String.join("_", userIds);
		}
	}
	// ---------------------------------------------------------

	public List<String> findAllConversation() {
		DatabaseReference databaseRef = firebaseDatabase.getReference("RealTimeChat");
		List<String> listConversation = new ArrayList<>();

		try {
			CompletableFuture<Void> future = new CompletableFuture<>();
			databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
						String chatNodeName = childSnapshot.getKey();
						listConversation.add(chatNodeName);
					}
					future.complete(null);
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {
				}
			});
			try {
				future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Đợi cho đến khi dữ liệu được lấy xong
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return listConversation;
	}

	public List<Long> findAllUserIdsInConversations(long userId) {
		List<Long> userIds = new ArrayList<>();
		List<String> conversations = findAllConversation();

		for (String conversation : conversations) {
			try {
				long otherUserId = getUser2IdFromConversationId(conversation, userId);
				if (otherUserId != -1) {
					userIds.add(otherUserId);
				}
			} catch (IllegalArgumentException e) {
				// Handle the case where the conversation or userId is invalid
				System.err.println(e.getMessage());
			}
		}

		return userIds;
	}

	public static long getUser2IdFromConversationId(String conversationId, long userId1) {
		String[] userIds = conversationId.split("_");

		if (userIds.length == 2) {
			long user1 = Long.parseLong(userIds[0]);
			long user2 = Long.parseLong(userIds[1]);

			if (user1 == userId1) {
				return user2;
			} else if (user2 == userId1) {
				return user1;
			}
		}
		return -1;
	}

	public void deleteConversation(Long userId1, Long userId2) {
		String conversationId = generateConversationId(userId1, userId2);
		DatabaseReference conversationRef = firebaseDatabase.getReference("RealTimeChat").child(conversationId);

		conversationRef.removeValue(new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
				if (databaseError == null) {
					System.out.println("Conversation deleted successfully");
				} else {
					System.err.println("Error deleting conversation: " + databaseError.getMessage());
				}
			}
		});
	}
}