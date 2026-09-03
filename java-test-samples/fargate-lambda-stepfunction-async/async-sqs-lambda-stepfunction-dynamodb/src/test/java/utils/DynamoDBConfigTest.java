package utils;

import com.amazon.aws.sample.utils.AmazonDynamoDBClientBuilderService;
import com.amazon.aws.sample.utils.DynamoDBConfig;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = {PropertyPlaceholderAutoConfiguration.class, DynamoDBConfig.class})
public class DynamoDBConfigTest {
  @Mock
  private DynamoDbClient dynamoDbClientMock;
  @Mock
  private AmazonDynamoDBClientBuilderService amazonDynamoDBClientBuilderService;
  @InjectMocks
  private DynamoDBConfig dynamoDBConfig;

  @Test
  public void testGetOrderStatus() {

    String orderId = "abee61d5-e180-4d01-bdb5-f7ce31562a81";

    when(amazonDynamoDBClientBuilderService.buildClient()).thenReturn(dynamoDbClientMock);

    Map<String, AttributeValue> keysMap = new HashMap<>();
    keysMap.put("orderId", AttributeValue.builder().s(orderId).build());

    GetItemRequest request = GetItemRequest.builder()
            .tableName("order_details")
            .projectionExpression("orderStatus")
            .key(keysMap)
            .build();

    Map<String, AttributeValue> item = new HashMap<>();
    item.put("orderStatus", AttributeValue.builder().s("Order Placed").build());

    GetItemResponse result = GetItemResponse.builder()
            .item(item)
            .build();

    when(dynamoDbClientMock.getItem(request)).thenReturn(result);
    dynamoDBConfig.getOrderStatus(orderId);
    assertThat(result.item().get("orderStatus").s()).isEqualTo("Order Placed");
  }
}
