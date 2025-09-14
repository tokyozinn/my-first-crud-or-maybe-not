#!/bin/bash
set -euo pipefail
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1

TOPIC="notifications"
EMAIL="dev@local.test"

TOPIC_ARN=$(aws --endpoint-url=http://localhost:4566 sns create-topic \
  --name "$TOPIC" --query 'TopicArn' --output text)

SUB_ARN=$(aws --endpoint-url=http://localhost:4566 sns subscribe \
  --topic-arn "$TOPIC_ARN" --protocol email \
  --notification-endpoint "$EMAIL" \
  --return-subscription-arn --query 'SubscriptionArn' --output text || true)

TOKEN=$(curl -s "http://localhost:4566/_aws/sns/subscription-tokens/$SUB_ARN" \
  | jq -r .subscription_token)
aws --endpoint-url=http://localhost:4566 sns confirm-subscription \
  --topic-arn "$TOPIC_ARN" --token "$TOKEN" --authenticate-on-unsubscribe true >/dev/null

echo "SNS topic: $TOPIC_ARN"
echo "Email subscription confirmed: $SUB_ARN"
